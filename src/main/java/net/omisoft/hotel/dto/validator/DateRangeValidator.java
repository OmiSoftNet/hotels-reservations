package net.omisoft.hotel.dto.validator;

import lombok.SneakyThrows;
import net.omisoft.hotel.configuration.MessageSourceConfiguration;
import net.omisoft.hotel.dto.validator.annotation.ValidateDateRange;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateRangeValidator implements ConstraintValidator<ValidateDateRange, Object> {

    @Autowired
    private MessageSourceConfiguration message;

    private String start;
    private String end;
    private boolean futureOrPresent;
    private String messageFutureOrPresent;
    private LocalDate current;

    @SneakyThrows
    @Override
    public void initialize(ValidateDateRange constraintAnnotation) {
        start = constraintAnnotation.start();
        end = constraintAnnotation.end();
        futureOrPresent = constraintAnnotation.futureOrPresent();
        messageFutureOrPresent = constraintAnnotation.messageFutureOrPresent();
        current = LocalDate.now();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field fieldStart = value.getClass().getDeclaredField(start);
            Field fieldEnd = value.getClass().getDeclaredField(end);
            if (fieldStart.getType() == LocalDate.class && fieldEnd.getType() == LocalDate.class) {
                fieldStart.setAccessible(true);
                fieldEnd.setAccessible(true);
                if (fieldStart.get(value) == null && fieldEnd.get(value) == null) {
                    return true;
                }
                LocalDate dateStart = LocalDate.parse(String.valueOf(fieldStart.get(value)));
                LocalDate dateEnd = LocalDate.parse(String.valueOf(fieldEnd.get(value)));
                if (futureOrPresent && dateStart.isBefore(current)) {
                    addConstraintViolation(context);
                    return false;
                }
                if (!dateStart.isBefore(dateEnd)) {
                    addConstraintViolation(context);
                    return false;
                }
            } else {
                addConstraintViolation(context);
                return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException | DateTimeParseException e) {
            addConstraintViolation(context);
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String msg = message.getMessage(context.getDefaultConstraintMessageTemplate());
        if (futureOrPresent) {
            msg = msg + ", " + message.getMessage(messageFutureOrPresent);
        }
        context.buildConstraintViolationWithTemplate(msg).addPropertyNode(start).addConstraintViolation();
    }

}

