package gti.aggregatorservice.service;

import gti.aggregatorservice.dto.TransactionEvent;
import gti.aggregatorservice.repo.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AggregatorService {
    MerchantLoaderService merchantLoaderService;
    TransactionRepo transactionRepo;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    public AggregatorService(MerchantLoaderService merchantLoaderService, TransactionRepo transactionRepo) {
        this.merchantLoaderService = merchantLoaderService;
        this.transactionRepo = transactionRepo;
    }


    public void aggregate(TransactionEvent transactionEvent) {

        var merchant =  merchantLoaderService.getCategory(transactionEvent.getMerchantId());
        if(merchant != null){
            transactionEvent.setCategory(merchant.getCategory());
            transactionEvent.setMerchant(merchant.getMerchant());
        }


        try {
            transactionRepo.save(transactionEvent);
            logger.info("Saved transaction event: {}", transactionEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
