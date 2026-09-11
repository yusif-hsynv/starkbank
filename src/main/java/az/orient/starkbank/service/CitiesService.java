package az.orient.starkbank.service;

import az.orient.starkbank.model.Cities;

import java.util.List;
import java.util.UUID;

public interface CitiesService {
    List<Cities> getAllCities();

    Cities getCitiesById(UUID id);

    Cities createCities(Cities cities);

    Cities updateCities(UUID id, Cities cities);

    void deleteCities(UUID id);
}
