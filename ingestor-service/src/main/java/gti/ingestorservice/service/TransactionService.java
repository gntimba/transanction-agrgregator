package gti.ingestorservice.service;

import gti.ingestorservice.dto.TransactionIncoming;
import gti.ingestorservice.producer.kafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TransactionService {
    @Autowired
    private kafkaProducer kafkaProducer;
    @Autowired
    TransactionIncomingValidator transactionIncomingValidator;

    public String publish(TransactionIncoming transactionIncoming) {
        transactionIncoming.setCreatedDate(new Date());
        try {
            transactionIncomingValidator.validate(transactionIncoming);
            kafkaProducer.publish(transactionIncoming);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";

    }
}
