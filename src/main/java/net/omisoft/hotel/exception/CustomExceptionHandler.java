package net.omisoft.hotel.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import net.omisoft.hotel.configuration.MessageSourceConfiguration;
import net.omisoft.hotel.pojo.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;

@RestControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler {

    private final MessageSourceConfiguration message;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
    })
    protected CustomMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        return new CustomMessage(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class
    })
    protected CustomMessage handleConstraintViolationException(ConstraintViolationException ex) {
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().stream()
                .findFirst()
                .get();
        String message = constraintViolation.getMessage();
        String property = constraintViolation.getPropertyPath().toString();
        if (property.contains(".")) {
            property = property.substring(property.indexOf(".") + 1);
        }
        return new CustomMessage(message, property);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    protected CustomMessage handleValidationException(Exception ex) throws Exception {
        BindingResult result;
        switch (ex.getClass().getSimpleName()) {
            case "MethodArgumentNotValidException":
                result = ((MethodArgumentNotValidException) ex).getBindingResult();
                break;
            case "BindException":
                result = ((BindException) ex).getBindingResult();
                break;
            default:
                throw ex;
        }
        FieldError fieldError = result.getFieldError();
        if (fieldError != null) {
            String msg = message.getMessage(fieldError.getDefaultMessage());
            String propertyName = fieldError.getField();
            try {
                Field field = result.getTarget().getClass().getDeclaredField(propertyName);
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    propertyName = field.getAnnotation(JsonProperty.class).value();
                }
            } catch (NoSuchFieldException exc) {
            }
            return new CustomMessage(msg, propertyName);
        } else {
            ObjectError objectError = result.getAllErrors().stream()
                    .findFirst()
                    .get();
            return new CustomMessage(objectError.getDefaultMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoSuchElementException.class,
    })
    protected CustomMessage handleNoSuchElementException(NoSuchElementException ex) {
        return new CustomMessage(ex.getMessage());
    }

}
