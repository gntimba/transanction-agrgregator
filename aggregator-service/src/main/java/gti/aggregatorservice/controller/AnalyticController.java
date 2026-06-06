package gti.aggregatorservice.controller;

import gti.aggregatorservice.dto.*;
import gti.aggregatorservice.service.AnalysisService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    List<TopMerchant> getTopMerchants() {
        return analysisService.getTopMerchants();
    }

    @GetMapping("/spendPerCategory")
    List<SpendPerCategory> getSpendPerCategory(@RequestParam String account) {
        return analysisService.getSpendPerCategory(account);
    }

    @GetMapping("/TotalSpendBetweenDates")
    public BigDecimal getTotalSpendBetweenDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd,
                                                @RequestParam String account) {


        Date start = Date.from(dateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endExclusive = Date.from(dateEnd.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return analysisService.getTotalSpendBetweenDates(start, endExclusive,account);
    }
    @GetMapping("/findByMerchantId/{merchant}")
    List<TransactionEvent> findByMerchantId(@PathVariable Integer merchant) {
        return analysisService.findByMerchantId(merchant);
    }



}
