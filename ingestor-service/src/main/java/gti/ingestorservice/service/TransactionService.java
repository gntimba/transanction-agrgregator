package gti.ingestorservice.service;

import gti.ingestorservice.dto.TransactionEvent;
import gti.ingestorservice.producer.kafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TransactionService {
    @Autowired
    private kafkaProducer kafkaProducer;

    public String publish(TransactionEvent transactionEvent) {
        transactionEvent.setCreatedDate(new Date());
        try {
            kafkaProducer.publish(transactionEvent);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";

    }
}
