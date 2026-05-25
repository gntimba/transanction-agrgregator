package gti.aggregatorservice.producer;

import gti.aggregatorservice.dto.TransactionEvent;
import gti.aggregatorservice.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    AggregatorService aggregatorService;
    public KafkaProducer(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @KafkaListener(topics = "transactions")
    public void consume(TransactionEvent event) {

        logger.info("Received transaction event: {}", event);
        aggregatorService.aggregate(event);

    }
}
