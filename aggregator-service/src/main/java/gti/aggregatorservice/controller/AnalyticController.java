package gti.aggregatorservice.controller;

import gti.aggregatorservice.dto.DailyTxnCount;
import gti.aggregatorservice.dto.MonthlyTotal;
import gti.aggregatorservice.dto.TopMerchant;
import gti.aggregatorservice.dto.TransactionEvent;
import gti.aggregatorservice.service.AnalysisService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("analytics")
public class AnalyticController {
    AnalysisService analysisService;

    public AnalyticController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/txnPeriod")
    public List<TransactionEvent> transBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) {


        Date start = Date.from(dateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endExclusive = Date.from(dateEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return analysisService.txnBetween(start, endExclusive);
    }

    @GetMapping("/dailyTxnCount")
    public List<DailyTxnCount> getDailyTransactionCount() {
        return analysisService.getDailyTransactionCount();
    }

    @GetMapping("/monthlySpend")
    public List<MonthlyTotal> getMonthlySpend() {
        return analysisService.getMonthlySpend();
    }
    @GetMapping("/topMerchant")
    List<TopMerchant> getTopMerchants(){
       return analysisService.getTopMerchants();
    }
}
