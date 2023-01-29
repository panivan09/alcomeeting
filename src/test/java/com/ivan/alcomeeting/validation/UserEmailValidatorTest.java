package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserEmailValidatorTest {
    @InjectMocks
    private UserEmailValidator userEmailValidator;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    // 1 - null -> exception
    // 2 - empty -> exc
    // 3 - not email -> exc
    // 4 - email - no exc

    @Test
    public void validate_throwValidationExceptionIfEmailDoesNotMatchEmailPattern() {
        // Given
        String email = "lala.com";
        String message = "Incorrect email";

        // When and Then
        assertThatThrownBy(() -> userEmailValidator.validate(email))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_throwValidationExceptionIfEmailIsNull() {
        // Given
        String email = null;
        String message = "User email should not be null or empty";

        // When and Then
        assertThatThrownBy(() -> userEmailValidator.validate(email))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_throwValidationExceptionIfEmailIsEmpty() {
        // Given
        String email = "";
        String message = "User email should not be null or empty";

        // When and Then
        assertThatThrownBy(() -> userEmailValidator.validate(email))
                .isInstanceOf(ValidationException.class)
                .hasMessage(message);
    }

    @Test
    public void validate_doesNotThrowValidationExceptionIfEmailIsCorrect() {
        // Given
        String email = "lala@gmail.com";

        // When and Then
        assertThatCode(() -> userEmailValidator.validate(email))
                .doesNotThrowAnyException();
    }
}