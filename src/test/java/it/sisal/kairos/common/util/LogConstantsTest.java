package it.sisal.kairos.common.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class LogConstantsTest {

    @Test
    void testLogMessageReceivingSuccess() {
        Logger logger = Mockito.mock(Logger.class);
        LogConstants.logMessageReceiving(logger, "testId", true, "", "TestComponent");
        verify(logger).info(LogConstants.LOG_KAIROS_STARTUP + " " + LogConstants.LOG_RESPONSE_RECEIVING_OK + ": " + LogConstants.LOG_CHANNEL_INITIALIZED, "testId", "TestComponent");
    }

    @Test
    void testLogMessageReceivingFailure() {
        Logger logger = Mockito.mock(Logger.class);
        LogConstants.logMessageReceiving(logger, "testId", false, "ErrorMessage", "TestComponent");
        verify(logger).info(LogConstants.LOG_RESPONSE_RECEIVING_KO, "testId", "TestComponent", "ErrorMessage");
    }

    @Test
    void testLogMessageReceivingPing() {
        Logger logger = Mockito.mock(Logger.class);
        LogConstants.logMessageReceiving(logger, MsgConstants.MSG_ID_PING, true, "", "TestComponent");
        verify(logger).info(LogConstants.LOG_MESSAGE_RECEIVING, MsgConstants.MSG_ID_PING, "TestComponent");
    }

    @Test
    void testLogCommunicationError() {
        Logger logger = Mockito.mock(Logger.class);
        Throwable throwable = new RuntimeException("Test Exception");
        LogConstants.logCommunicationError(logger, throwable, "TestComponent");
        verify(logger).error(LogConstants.LOG_COMMUNICATION_ERROR, "TestComponent", throwable.getMessage());
    }
}
