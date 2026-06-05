package gti.ingestorservice.service;

import gti.ingestorservice.dto.TransactionIncoming;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class TransactionIncomingValidator {

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
