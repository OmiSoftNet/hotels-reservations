package net.omisoft.hotel.dto.validator.annotation;

import net.omisoft.hotel.dto.validator.DateRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidateDateRange {

    String message() default "exception.date_range.validate";

    String messageFutureOrPresent() default "exception.date_range.validate.future_or_present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String start();

    String end();

    boolean futureOrPresent() default false;

}
