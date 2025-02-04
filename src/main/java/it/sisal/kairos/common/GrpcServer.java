package it.sisal.kairos.common;

import io.grpc.stub.StreamObserver;
import it.sisal.kairos.common.model.Client;
import it.sisal.kairos.common.util.MsgConstants;
import it.sisal.kairos.grpc.AckResponse;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentMap;

public interface GrpcServer {

    default void checkActiveClients(ConcurrentMap<Client, StreamObserver<AckResponse>> clients, long checkInterval, Logger logger) {
        long currentTime = System.currentTimeMillis();
        if (clients.isEmpty()) {
            logger.warn("No active clients found.");
        } else {
            clients.forEach((clientKey, clientStream) -> {
                logger.info("Checking client [{}]", clientKey.getClientId());
                long clientTime = clientKey.getCurrentTimeMillis();
                if (currentTime - clientTime > checkInterval) {
                    logger.info("Sending ping to client [{}]", clientKey.getClientId());
                    // ping client sending a message with recordId = MSG_ID_PING
                    AckResponse pingMessage = AckResponse.newBuilder().setRecordId(MsgConstants.MSG_ID_PING).build();
                    try {
                        clientStream.onNext(pingMessage);
                    } catch (Exception e) {
                        logger.warn("Error in sending ping to client [{}]: {}", clientKey.getClientId(), e.getMessage());
                    }

                    // remove the client so that it can be eventually re-registered when monitor receives 'pong'
                    clients.remove(clientKey);
                } else {
                    logger.info("No need to ping client [{}]", clientKey.getClientId());
                }
            });
        }
    }

}
