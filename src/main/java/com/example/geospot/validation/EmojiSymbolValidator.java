package com.example.geospot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.function.IntPredicate;

public class EmojiSymbolValidator implements ConstraintValidator<EmojiSymbol, String> {

    @Override
    public void initialize(EmojiSymbol constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        IntPredicate isEmoji = Character::isEmoji;
        IntPredicate isEmojiComponent = Character::isEmojiComponent;
        IntPredicate isValidEmoji = isEmoji.or(isEmojiComponent);
        return value != null && value.codePoints().allMatch(isValidEmoji);
    }
}


