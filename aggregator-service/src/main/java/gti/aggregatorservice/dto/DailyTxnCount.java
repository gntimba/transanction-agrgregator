package gti.aggregatorservice.dto;

import java.util.Date;

public interface DailyTxnCount {
  Date getTransactionDate();
  Integer getTotal();
}
