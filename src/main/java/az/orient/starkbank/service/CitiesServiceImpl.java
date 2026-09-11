package az.orient.starkbank.service;

import az.orient.starkbank.model.Cities;
import az.orient.starkbank.repository.redis.CitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitiesServiceImpl implements CitiesService {
    private final CitiesRepository citiesRepository;

    @Override
    public List<Cities> getAllCities() {
        return citiesRepository.findAll();
    }

    @Override
    public Cities getCitiesById(UUID id) {
        return citiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find city by id: " + id));
    }

    @Override
    public Cities createCities(Cities cities) {
        return citiesRepository.save(cities);
    }

    @Override
    public Cities updateCities(UUID id, Cities cities) {
        Cities oldCity = getCitiesById(id);

        oldCity.setName(cities.getName());
        oldCity.setPlateCode(cities.getPlateCode());
        return citiesRepository.save(oldCity);
    }

    @Override
    public void deleteCities(UUID id) {
        Cities city = getCitiesById(id);
        citiesRepository.delete(city);
    }
}
