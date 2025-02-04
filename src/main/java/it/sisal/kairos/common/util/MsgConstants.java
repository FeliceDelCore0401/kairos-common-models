package it.sisal.kairos.common.util;

public class MsgConstants {

    public static final String MSG_ID_HELLO = "HELLO";
    public static final String MSG_ID_PING = "PING";
    public static final String MSG_ID_PONG = "PONG";

    public static final String COMPONENT_MONITOR = "monitor";
    public static final String COMPONENT_SENDER_REST = "senderRest";
    public static final String COMPONENT_SENDER_VAULT = "senderVault";

    private MsgConstants() {
        throw new IllegalStateException("Utility class");
    }
    
}
