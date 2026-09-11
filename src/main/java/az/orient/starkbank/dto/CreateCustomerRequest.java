package az.orient.starkbank.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;
}
