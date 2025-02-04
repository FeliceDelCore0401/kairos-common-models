package it.sisal.kairos.common.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StructConversionExceptionTest {

    @Test
    void testExceptionMessageAndCause() {
        Throwable cause = new RuntimeException("Cause message");
        StructConversionException exception = new StructConversionException("Test message", cause);
        
        assertEquals("Test message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
