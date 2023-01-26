package com.ivan.alcomeeting.validation.view;

import org.junit.jupiter.api.Test;

class ValidationUtilsTest {

    @Test
    public void testExc1() {
        // Given
        // When
        ValidationUtils.validateNotNullOrEmpty(null, "Custom message");
        // Check exception with message
    }

    @Test
    public void testExc2() {
        // Given
        // When
        ValidationUtils.validateNotNullOrEmpty("", "Custom message");
        // Check exception with message
    }

    @Test
    public void testMethod() {
        // Given
        // When
        ValidationUtils.validateNotNullOrEmpty("nonEmptyString", "Custom message");
    }
}