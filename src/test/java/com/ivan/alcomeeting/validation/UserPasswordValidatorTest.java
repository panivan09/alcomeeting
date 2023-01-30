package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserPasswordValidatorTest {
    @InjectMocks
    private UserPasswordValidator userPasswordValidator;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validate_throwValidationExceptionIfPasswordDoesNotMatchPasswordPattern() {
        // Given
        String password = "pas";
        String message = "Password must contain at least 8 characters, including UPPER/lowercase and numbers";

        // When and Then
        assertThatThrownBy(() -> userPasswordValidator.validate(password))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_throwValidationExceptionIfPasswordIsNull() {
        // Given
        String password = null;
        String message = "User password should not be null or empty";

        // When and Then
        assertThatThrownBy(() -> userPasswordValidator.validate(password))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_throwValidationExceptionIfPasswordIsEmpty() {
        // Given
        String password = "";
        String message = "User password should not be null or empty";

        // When and Then
        assertThatThrownBy(() -> userPasswordValidator.validate(password))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_doesNotThrowValidationExceptionIfPasswordIsCorrect() {
        // Given
        String password = "Password123";

        // When and Then
        assertThatCode(() -> userPasswordValidator.validate(password))
                .doesNotThrowAnyException();
    }
}