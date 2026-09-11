package az.orient.starkbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;
}
