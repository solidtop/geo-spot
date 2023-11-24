package com.example.geospot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Range(min = -90, max = 90)
public @interface Latitude {
    String message() default "Latitude must be between -90 and 90";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
