package gti.aggregatorservice.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity()
@Table(
        name = "transaction",
        indexes = {
                @Index(name = "idx_account_id", columnList = "accountId"),
                @Index(name = "idx_transaction_date", columnList = "transactionDate"),
                @Index(name = "idx_category", columnList = "category"),
                @Index(name = "idx_merchant", columnList = "merchant")
        }
)
public class TransactionEvent {
    @Id
    private UUID id;

    @NotBlank(message = "customer_id is required")
    private String accountId;

    private String merchant;
    private Integer merchantId;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "amount must be positive")
    @Digits(integer = 16, fraction = 2)
    @Column(precision = 16, scale = 2)
    private BigDecimal amount;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    @Builder.Default
    private String currency = "ZAR";

    @NotBlank(message = "source is required")
    private String source;

    @NotNull(message = "transaction_date is required")
    private Date transactionDate;
    private String category;
    private Date createdDate;
}
