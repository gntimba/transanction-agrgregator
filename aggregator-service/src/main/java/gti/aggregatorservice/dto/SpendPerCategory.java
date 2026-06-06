package gti.aggregatorservice.dto;

import java.math.BigDecimal;

public interface SpendPerCategory {
    String getCategory();
    BigDecimal getTotal();
}
