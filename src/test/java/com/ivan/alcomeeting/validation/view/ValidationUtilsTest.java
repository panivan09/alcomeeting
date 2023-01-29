package com.ivan.alcomeeting.validation.view;

import com.ivan.alcomeeting.exception.ValidationException;
import org.junit.jupiter.api.Test;


import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationUtilsTest {

    private static final Pattern TEST_PATTERN = Pattern.compile("^[a-z]{4}$");

    @Test
    public void validateNotNullOrEmpty_throwValidationExceptionIfValueIsNull() {
        // Given
        String value = null;
        String message = "Value can not be null";

        // When and Then
        assertThatThrownBy(()-> ValidationUtils.validateNotNullOrEmpty(value, message))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validateNotNullOrEmpty_throwValidationExceptionIfValueIsEmpty() {
        // Given
        String value = "";
        String message = "Value can not be Empty";

        // When and Then
        assertThatThrownBy(()-> ValidationUtils.validateNotNullOrEmpty(value, message))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validateNotNullOrEmpty_doesNotThrowAnyExceptionIfValueIsNotEmptyOrNull() {
        // Given
        String value = "Any";
        String message = "Value can not be Empty or null";

        // When and Then
        assertThatCode(()-> ValidationUtils.validateNotNullOrEmpty(value, message))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateCollectionNotNullOrEmpty_throwValidationExceptionIfValueIsNull() {
        // Given
        Collection value = null;
        String message = "Value can not be null";

        // When and Then
        assertThatThrownBy(()-> ValidationUtils.validateCollectionNotNullOrEmpty(value, message))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validateCollectionNotNullOrEmpty_throwValidationExceptionIfValueIsEmpty() {
        // Given
        Collection value = Collections.emptySet();
        String message = "Value can not be Empty";

        // When and Then
        assertThatThrownBy(()-> ValidationUtils.validateCollectionNotNullOrEmpty(value, message))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validateCollectionNotNullOrEmpty_doesNotThrowAnyExceptionIfValueIsNotEmptyOrNull() {
        // Given
        Collection value = Set.of("Any", "AnyAny");
        String message = "Value can not be Empty";

        // When and Then
        assertThatCode(()-> ValidationUtils.validateCollectionNotNullOrEmpty(value, message))
                .doesNotThrowAnyException();
    }

    @Test
    public void validateStringByPattern_throwValidationExceptionIfValueDoesNotMatchPattern() {
        // Given
        String value = "lala.com";
        String message = "Incorrect Email";

        // When and Then
        assertThatThrownBy(()-> ValidationUtils.validateStringByPattern(value, TEST_PATTERN, message))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validateStringByPattern_doesNotThrowValidationExceptionIfValueMatchesPattern() {
        // Given
        String value = "lala";
        String message = "Incorrect Email";

        // When and Then
        assertThatCode(()-> ValidationUtils.validateStringByPattern(value, TEST_PATTERN, message))
                .doesNotThrowAnyException();
    }
}