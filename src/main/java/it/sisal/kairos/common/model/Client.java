package it.sisal.kairos.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Client {

    private String clientId;
    private long currentTimeMillis;

    public Client(String clientId) {
        this.clientId = clientId;
        this.currentTimeMillis = System.currentTimeMillis();
    }

}