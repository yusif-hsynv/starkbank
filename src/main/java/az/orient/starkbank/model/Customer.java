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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer dateOfBirth;
    private String address;

    @Enumerated(EnumType.STRING)
    private City city;
}
