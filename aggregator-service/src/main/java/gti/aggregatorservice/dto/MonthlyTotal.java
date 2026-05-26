package gti.aggregatorservice.dto;

import java.math.BigDecimal;

public interface MonthlyTotal {
    String getMonth();
    BigDecimal getTotal();
}
