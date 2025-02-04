package it.sisal.kairos.common.util;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class MsgConstantsTest {

    @Test
    void testConstantsValues() {
        assertEquals("HELLO", MsgConstants.MSG_ID_HELLO);
        assertEquals("PING", MsgConstants.MSG_ID_PING);
        assertEquals("PONG", MsgConstants.MSG_ID_PONG);

        assertEquals("monitor", MsgConstants.COMPONENT_MONITOR);
        assertEquals("senderRest", MsgConstants.COMPONENT_SENDER_REST);
        assertEquals("senderVault", MsgConstants.COMPONENT_SENDER_VAULT);
    }

    @Test
    void testUtilityClassConstructor() throws Exception {
        Constructor<MsgConstants> constructor = MsgConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Utility class", exception.getCause().getMessage());
    }
}
