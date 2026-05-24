package gti.aggregatorservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEvent {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "customer_id is required")
    private String accountId;

    @NotBlank(message = "merchant is required")
    @Size(max = 255)
    private String merchant;

    private String merchantId;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "amount must be positive")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    @Builder.Default
    private String currency = "USD";

    @NotBlank(message = "source is required")
    private String source;

    @NotNull(message = "transaction_date is required")
  //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transactionDate;

    private Date createdDate;
}
