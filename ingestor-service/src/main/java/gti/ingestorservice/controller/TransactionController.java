package gti.ingestorservice.controller;

import gti.ingestorservice.dto.TransactionIncoming;
import gti.ingestorservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("v1/transaction")
    public String transactionEvent(@RequestBody TransactionIncoming transactionIncoming) {

        return transactionService.publish(transactionIncoming);
    }
}
