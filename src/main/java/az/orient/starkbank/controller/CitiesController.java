package az.orient.starkbank.controller;

import az.orient.starkbank.model.Cities;
import az.orient.starkbank.service.CitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/cities")
@RequiredArgsConstructor
public class CitiesController {
    private final CitiesService citiesService;

    @GetMapping
    public ResponseEntity<List<Cities>> getAllCities() {
        return ResponseEntity.ok(citiesService.getAllCities());
    }

    @PostMapping
    public ResponseEntity<Cities> createCities(@RequestBody Cities cities) {
        return ResponseEntity.ok(citiesService.createCities(cities));
    }
}
