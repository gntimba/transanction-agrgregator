package gti.ingestorservice.service;

import gti.ingestorservice.dto.MerchantCategory;
import gti.ingestorservice.dto.TransactionResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class ExternalClient {

    private final RestClient restClient;

    public ExternalClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public MerchantCategory getMerchant(Integer merchantId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/merchant")
                        .queryParam("merchant", merchantId)
                        .build())
                .retrieve()
                .body(MerchantCategory.class);
    }

    public TransactionResponse getbyID(UUID id){
        return restClient.get()
                .uri("/api/v1/tran?id={id}", id)
                .retrieve()
                .body(TransactionResponse.class);
    }
}