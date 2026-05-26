package gti.aggregatorservice.service;

import gti.aggregatorservice.dto.DailyTxnCount;
import gti.aggregatorservice.dto.MonthlyTotal;
import gti.aggregatorservice.dto.TopMerchant;
import gti.aggregatorservice.dto.TransactionEvent;
import gti.aggregatorservice.repo.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnalysisService {

    TransactionRepo transactionRepo;

    public AnalysisService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }


    public List<TransactionEvent> txnBetween(Date start, Date endExclusive) {
        return transactionRepo.findByTransactionDateBetween(start, endExclusive);
    }

    public List<DailyTxnCount> getDailyTransactionCount() {
        return transactionRepo.getDailyTransactionCount();
    }

    public List<MonthlyTotal> getMonthlySpend() {
       return transactionRepo.getMonthlySpend();
    }

    public List<TopMerchant> getTopMerchants() {
        return transactionRepo.getTopMerchants();
    }
}
