package az.orient.starkbank.dto;

import az.orient.starkbank.model.Currency;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private UUID id;
    private UUID customerId;
    private Double balance;
    private Currency currency;
}
