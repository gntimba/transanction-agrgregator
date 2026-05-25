package gti.aggregatorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantCategory {
    private Integer id;
    private String merchant;
    private String category;
}
