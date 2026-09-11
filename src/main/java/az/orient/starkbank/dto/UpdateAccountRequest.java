package az.orient.starkbank.dto;

import az.orient.starkbank.model.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private City city;
}
