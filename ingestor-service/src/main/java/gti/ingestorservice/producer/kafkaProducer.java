package gti.ingestorservice.producer;

import gti.ingestorservice.dto.TransactionIncoming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class kafkaProducer {
    @Autowired
    private KafkaTemplate<String, TransactionIncoming> kafkaTemplate;

    public void publish(TransactionIncoming event) {
        kafkaTemplate.send("transactions", event);
    }
}
