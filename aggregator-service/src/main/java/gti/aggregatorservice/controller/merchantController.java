package gti.aggregatorservice.controller;

import gti.aggregatorservice.dto.MerchantCategory;
import gti.aggregatorservice.dto.TransactionEvent;
import gti.aggregatorservice.service.AggregatorService;
import gti.aggregatorservice.service.MerchantLoaderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class merchantController {
    private MerchantLoaderService merchantLoaderService;
    private AggregatorService aggregatorService;
    public merchantController(MerchantLoaderService merchantLoaderService, AggregatorService aggregatorService) {
            this.merchantLoaderService = merchantLoaderService;
            this.aggregatorService = aggregatorService;
    }
    @GetMapping("v1/categores")
    public Set<String> getCategory() {
        return merchantLoaderService.getCategories();
    }
    @GetMapping("v1/merchants")
    public List<MerchantCategory> getMerchants() {
      return   merchantLoaderService.getMerchant();
    }


    @GetMapping("v1/merchant")
    public MerchantCategory getMerchant(@RequestParam int merchant) {
        var value = merchantLoaderService.getCategory(merchant);
        if(value==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "merchant not found"
            );
        }
        return value;
    }

    @GetMapping("v1/tran")
    public Optional<TransactionEvent> getbyID(@RequestParam UUID id) {
        var value = aggregatorService.findById(id);
        if(value.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "id not found"
            );
        }
        return value;
    }

}
