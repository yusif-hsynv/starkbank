package az.orient.starkbank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID customerId;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private City city;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
