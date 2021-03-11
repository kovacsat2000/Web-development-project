package inf.unideb.hu.controller;

import inf.unideb.hu.exception.city.CityInUseException;
import inf.unideb.hu.exception.city.InvalidCityException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.City;
import inf.unideb.hu.service.CityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;

    @ApiOperation("Get cities")
    @GetMapping("/city")
    public Collection<CityDto> listAllCities(){
        return cityService.getAllCities()
                .stream()
                .map(model -> CityDto.builder()
                        .cityId(model.getCityId())
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation("Add city")
    @PostMapping("/city")
    public void recordCity(@RequestBody CityDto cityDto){
        try {
            cityService.recordCity(new City(cityDto.getCityId(), cityDto.getCity(), cityDto.getCountry()));
        }
        catch (InvalidCityException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }


    }

    @ApiOperation("Delete city")
    @DeleteMapping("/city/{id}")
    public void deleteCity(@RequestParam int cityId){
        try {
            City cityDto = cityService.readCityById(cityId);
            cityService.deleteCity(new City(cityDto.getCityId(), cityDto.getCity(), cityDto.getCountry()));
        } catch (CityInUseException | UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Update city")
    @PutMapping("/city")
    public void updateCity(@RequestBody CityUpdateDto cityUpdateDto){
        try {
            cityService.updateCity(
                    cityUpdateDto.getCity(),
                    new City(cityUpdateDto.getCityId(),
                            cityUpdateDto.getNewCity(),
                            cityUpdateDto.getNewCountry()));
        } catch (UnknownCountryException | InvalidCityException | UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}