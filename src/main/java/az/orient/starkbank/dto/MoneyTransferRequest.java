package az.orient.starkbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoneyTransferRequest {
    private UUID fromId;
    private UUID toId;
    private Double amount;
}
