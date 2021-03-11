package inf.unideb.hu.controller;

import inf.unideb.hu.exception.country.CountryInUseException;
import inf.unideb.hu.exception.country.InvalidCountryException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.Country;
import inf.unideb.hu.service.CountryService;
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
public class CountryController {

    private final CountryService countryService;

    @ApiOperation("Get addresses")
    @GetMapping("/country")
    public Collection<CountryDto> listAllCountries(){
        return countryService.getAllCountries()
                .stream()
                .map(model -> CountryDto.builder()
                        .country(model.getCountry())
                        .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation("Record address")
    @PostMapping("/country")
    public void recordCountry(@RequestBody CountryDto  countryDto){
        try {
            countryService.recordCounty(new Country(
                    countryDto.getCountry()
            ));
        } catch (InvalidCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Delete address")
    @DeleteMapping("/country")
    public void deleteCountry(@RequestBody CountryDto countryDto){
        try{
            countryService.deleteCountry(new Country(countryDto.getCountry()));
        }
        catch (UnknownCountryException | CountryInUseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Update address")
    @PutMapping("/country")
    public void updateCountry(@RequestBody CountryUpdateDto countryUpdateDto){
        try{
            countryService.updateCountry(new Country(countryUpdateDto.getCountry()),new Country(countryUpdateDto.getNewCountry()));
        }
        catch (InvalidCountryException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}