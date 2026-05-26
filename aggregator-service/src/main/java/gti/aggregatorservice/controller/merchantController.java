package gti.aggregatorservice.controller;

import gti.aggregatorservice.dto.MerchantCategory;
import gti.aggregatorservice.service.MerchantLoaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class merchantController {
    private MerchantLoaderService merchantLoaderService;
    public merchantController(MerchantLoaderService merchantLoaderService) {
            this.merchantLoaderService = merchantLoaderService;
    }
    @GetMapping("v1/categores")
    public Set<String> getCategory() {
        return merchantLoaderService.getCategories();
    }
    @GetMapping("v1/merchants")
    public List<MerchantCategory> getMerchants() {
      return   merchantLoaderService.getMerchant();
    }
}
