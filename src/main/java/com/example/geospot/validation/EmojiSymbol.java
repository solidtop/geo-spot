package com.example.geospot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EmojiSymbolValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface EmojiSymbol {
    String message() default "Symbol must be an emoji";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
