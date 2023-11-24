package com.example.geospot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Range(min = -180, max = 180)
public @interface Longitude {
    String message() default "Longitude must be between -180 and 180";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
