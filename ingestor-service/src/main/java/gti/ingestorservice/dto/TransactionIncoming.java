package gti.ingestorservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionIncoming {

    @NotBlank(message = "id is required")
    private UUID id;

    @NotBlank(message = "customer_id is required")
    private String accountId;

    @NotNull(message = "merchant_id is required")
    private Integer merchantId;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "amount must be positive")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    @Builder.Default
    private String currency = "ZAR";

    @NotBlank(message = "source is required")
    private String source;

    @NotNull(message = "transaction_date is required")
    private Date transactionDate;
    private Date createdDate;
}
