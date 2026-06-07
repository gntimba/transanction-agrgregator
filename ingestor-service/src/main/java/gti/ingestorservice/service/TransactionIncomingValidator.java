package gti.ingestorservice.service;

import gti.ingestorservice.exception.ValidationException;
import gti.ingestorservice.dto.TransactionIncoming;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionIncomingValidator {

    ExternalClient externalClient;

    public TransactionIncomingValidator(ExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    public void validate(TransactionIncoming transaction) {
        List<String> errrors = new ArrayList<>();


        if (transaction == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Request body is required"
            );
        }

        if (transaction.getId() == null) {
            errrors.add("id is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "id is required"
//            );
        }

        try {

            var response = externalClient.getbyID(transaction.getId());

            if (response != null) {
                errrors.add("id is in use");
//                throw new ResponseStatusException(
//                        HttpStatus.BAD_REQUEST,
//                        "id is in use"
//                );
            }

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                //  return; // ID not found, proceed
            }

            // throw ex;
        }

        if (transaction.getAccountId() == null || transaction.getAccountId().isBlank()) {
            errrors.add("accountId is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "customer_id is required"
//            );
        }

        if (transaction.getMerchantId() == null) {
            errrors.add("merchantId is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "merchant_id is required"
//            );
        }


        try {
            var responses = externalClient.getMerchant(transaction.getMerchantId());
        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                errrors.add("Merchant not found");
//                throw new ResponseStatusException(
//                        HttpStatus.BAD_REQUEST,
//                        "Merchant not found"
//                );
            } else
                throw ex;
        }


        if (transaction.getAmount() == null) {
            errrors.add("amount is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "amount is required"
//            );
        }

        if (transaction.getAmount() != null &&
                transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            errrors.add("amount must be positive");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "amount must be positive"
//            );
        }

        if (transaction.getCurrency() != null &&
                !transaction.getCurrency().matches("[A-Z]{3}")) {
            errrors.add("currency must be a 3-letter ISO code");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "currency must be a 3-letter ISO code"
//            );
        }

        if (transaction.getCurrency() != null &&
                !transaction.getCurrency().equals("ZAR")) {
            errrors.add("currency must be a ZAR");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "currency must be a 3-letter ISO code"
//            );
        }

        if (transaction.getSource() == null || transaction.getSource().isBlank()) {
            errrors.add("source is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "source is required"
//            );
        }

        if (transaction.getTransactionDate() == null) {
            errrors.add("transactionDate is required");
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "transaction_date is required"
//            );
        }


        if (!errrors.isEmpty()) {
            throw new ValidationException(errrors);
        }
    }
}
