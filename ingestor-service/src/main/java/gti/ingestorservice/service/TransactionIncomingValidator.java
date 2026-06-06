package gti.ingestorservice.service;

import gti.ingestorservice.dto.TransactionIncoming;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class TransactionIncomingValidator {

    ExternalClient externalClient;

    public TransactionIncomingValidator(ExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    public void validate(TransactionIncoming transaction) {


        if (transaction == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Request body is required"
            );
        }

        if (transaction.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id is required"
            );
        }

        try {

            var response = externalClient.getbyID(transaction.getId());

            if (response != null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "id is in use"
                );
            }

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
              //  return; // ID not found, proceed
            }

           // throw ex;
        }

        if (transaction.getAccountId() == null || transaction.getAccountId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "customer_id is required"
            );
        }

        if (transaction.getMerchantId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "merchant_id is required"
            );
        }


        try {
            var responses = externalClient.getMerchant(transaction.getMerchantId());
        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Merchant not found"
                );
            }

            throw ex;
        }


        if (transaction.getAmount() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "amount is required"
            );
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "amount must be positive"
            );
        }

        if (transaction.getCurrency() != null &&
                !transaction.getCurrency().matches("[A-Z]{3}")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "currency must be a 3-letter ISO code"
            );
        }

        if (transaction.getSource() == null || transaction.getSource().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "source is required"
            );
        }

        if (transaction.getTransactionDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "transaction_date is required"
            );
        }
    }
}
