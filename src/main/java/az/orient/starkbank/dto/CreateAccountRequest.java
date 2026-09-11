package az.orient.starkbank.dto;

import az.orient.starkbank.model.City;
import az.orient.starkbank.model.Currency;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    private UUID customerId;
    private Double balance;
    private Currency currency;
    private City city;
}
