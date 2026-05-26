package gti.aggregatorservice.service;

import gti.aggregatorservice.dto.MerchantCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
public class MerchantLoaderService {

    private Logger log = LoggerFactory.getLogger(MerchantLoaderService.class);

    private final Map<Integer, MerchantCategory> merchantCategories = new HashMap<>();
    private final Set<String> Categories = new HashSet<>();
    List<MerchantCategory> merchants = null;

    public MerchantLoaderService(ObjectMapper objectMapper) throws Exception {

        InputStream inputStream = new ClassPathResource("data/merchant.json")
                .getInputStream();

        TypeReference<List<MerchantCategory>> type = new TypeReference<>() {
        };

        merchants = objectMapper.readValue(inputStream, type);

        for (MerchantCategory merchant : merchants) {
            merchantCategories.put(
                    merchant.getId(),
                    merchant
            );
            Categories.add(merchant.getCategory());
        }

        log.info("Loaded merchants into memory");
    }

    public MerchantCategory getCategory(Integer merchant) {

        return merchantCategories.get(merchant);

    }

    public Set<String> getCategories() {
        return Categories;
    }
    public List<MerchantCategory> getMerchant() {
        return merchants;
    }
}

