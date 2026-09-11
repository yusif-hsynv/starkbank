package az.orient.starkbank.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceRequest {
    private Double amount;
}
