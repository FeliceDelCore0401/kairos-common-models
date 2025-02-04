package it.sisal.kairos.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.common.model.Client;
import it.sisal.kairos.grpc.AckResponse;

@ExtendWith(MockitoExtension.class)
class GrpcServerTest {

    private GrpcServer grpcServer;
    private ConcurrentMap<Client, StreamObserver<AckResponse>> clients;

    @Mock
    private StreamObserver<AckResponse> mockStreamObserver;

    @Mock
    private Logger mockLogger;

    @Mock
    private Client mockClient;

    private final long checkInterval = 1000L;

    @BeforeEach
    void setUp() {
        grpcServer = new GrpcServer() {};
        clients = new ConcurrentHashMap<>();
    }

    @Test
    void testCheckActiveClients_RemovesInactiveClient() {
        when(mockClient.getClientId()).thenReturn("client-1");
        when(mockClient.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis() - (checkInterval + 1));
        clients.put(mockClient, mockStreamObserver);

        grpcServer.checkActiveClients(clients, checkInterval, mockLogger);

        assertTrue(clients.isEmpty());
        verify(mockLogger, times(1)).info("Sending ping to client [{}]", "client-1");
        verify(mockStreamObserver, times(1)).onNext(any(AckResponse.class));
    }

    @Test
    void testCheckActiveClients_KeepsActiveClient() {
        when(mockClient.getClientId()).thenReturn("client-2");
        when(mockClient.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
        clients.put(mockClient, mockStreamObserver);

        grpcServer.checkActiveClients(clients, checkInterval, mockLogger);

        assertEquals(1, clients.size());
        verify(mockLogger, times(1)).info("No need to ping client [{}]", "client-2");
    }

    @Test
    void testCheckActiveClients_NoClients() {
        grpcServer.checkActiveClients(clients, checkInterval, mockLogger);
        verify(mockLogger, times(1)).warn("No active clients found.");
    }
}
