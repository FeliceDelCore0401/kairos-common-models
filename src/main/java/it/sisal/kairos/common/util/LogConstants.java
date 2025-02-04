package it.sisal.kairos.common.util;

import org.slf4j.Logger;

public class LogConstants {

    public static final String LOG_MSG_SENDING = "Sending [{}] message to [{}]";
    public static final String LOG_MESSAGE_RECEIVING = "Received [{}] message from [{}]";
    public static final String LOG_RESPONSE_RECEIVING_OK = "Received OK response for message [{}] from [{}]";
    public static final String LOG_RESPONSE_RECEIVING_KO = "Received KO response for message [{}] from [{}]: [{}]";
    public static final String LOG_COMMUNICATION_ERROR = "Error in communication with [{}]: {}";
    public static final String LOG_KAIROS_STARTUP = "#KAIROS# #STARTUP#";
    public static final String LOG_CHANNEL_INITIALIZED = "channel initialized";

    private LogConstants() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Log the receiving of a message
     * @param recordId the id of the message (it can be a custom message id or a response id)
     * @param success true if the response is successful, false otherwise
     * @param message the message (used in case of error response)
     * @param component the component that sent the received message
     */
    public static void logMessageReceiving(Logger logger, String recordId, boolean success, String message,
                                           String component) {
        if (recordId.equals(MsgConstants.MSG_ID_PING)) {
            // custom messages, that must not be treated as responses
            logger.info(LOG_MESSAGE_RECEIVING, recordId, component);
        } else {
            // normal responses
            if (success) {
                logger.info(LOG_KAIROS_STARTUP+" "+LOG_RESPONSE_RECEIVING_OK + ": "+LOG_CHANNEL_INITIALIZED, recordId, component);
            } else {
                logger.info(LOG_RESPONSE_RECEIVING_KO, recordId, component, message);
            }
        }
    }

    /**
     * Log a communication error
     * @param throwable the error
     * @param component the component that raised the error
     */
    public static void logCommunicationError(Logger logger, Throwable throwable, String component) {
        logger.error(LOG_COMMUNICATION_ERROR, component, throwable.getMessage());
    }

}
