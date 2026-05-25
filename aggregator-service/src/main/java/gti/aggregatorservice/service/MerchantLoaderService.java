package gti.aggregatorservice.service;

import gti.aggregatorservice.dto.MerchantCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MerchantLoaderService {

    private Logger log = LoggerFactory.getLogger(MerchantLoaderService.class);

    private final Map<Integer, MerchantCategory> merchantCategories = new HashMap<>();

    public MerchantLoaderService(ObjectMapper objectMapper) throws Exception {

        InputStream inputStream = new ClassPathResource("data/merchant.json")
                .getInputStream();

        TypeReference<List<MerchantCategory>> type = new TypeReference<>() {
        };

        List<MerchantCategory> merchants =
                objectMapper.readValue(inputStream, type);

        for (MerchantCategory merchant : merchants) {
            merchantCategories.put(
                    merchant.getId(),
                    merchant
            );
        }

        log.info("Loaded merchants into memory");
    }

    public MerchantCategory getCategory(Integer merchant) {

        return merchantCategories.get(merchant);

    }
}

