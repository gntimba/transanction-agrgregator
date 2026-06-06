package gti.ingestorservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TransactionResponse {

    private UUID id;
    private String accountId;

    private String merchant;
    private Integer merchantId;
    private BigDecimal amount;
    private String currency;
    private String source;
    private Date transactionDate;
    private String category;
    private Date createdDate;
}
