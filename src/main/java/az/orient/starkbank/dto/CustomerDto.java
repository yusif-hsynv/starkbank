package az.orient.starkbank.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private UUID id;
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;
}
