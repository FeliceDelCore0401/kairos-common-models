package it.sisal.kairos.common.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientDefaultConstructor() {
        Client client = new Client();
        assertNotNull(client);
        assertNull(client.getClientId());
        assertEquals(0, client.getCurrentTimeMillis());
    }

    @Test
    void testClientParameterizedConstructor() {
        String clientId = "client-123";
        Client client = new Client(clientId);
        assertEquals(clientId, client.getClientId());
        assertTrue(client.getCurrentTimeMillis() > 0);
    }

    @Test
    void testSettersAndGetters() {
        Client client = new Client();
        String clientId = "client-456";
        long currentTime = System.currentTimeMillis();
        
        client.setClientId(clientId);
        client.setCurrentTimeMillis(currentTime);
        
        assertEquals(clientId, client.getClientId());
        assertEquals(currentTime, client.getCurrentTimeMillis());
    }
}
