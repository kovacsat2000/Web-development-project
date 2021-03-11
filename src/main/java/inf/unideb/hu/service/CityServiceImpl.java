package inf.unideb.hu.service;

import inf.unideb.hu.dao.CityEntity;
import inf.unideb.hu.dao.CountryEntity;
import inf.unideb.hu.dao.city.CityRepository;
import inf.unideb.hu.dao.country.CountryRepository;
import inf.unideb.hu.exception.city.CityInUseException;
import inf.unideb.hu.exception.city.InvalidCityException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CountryEntity queryCountry(String country) throws UnknownCountryException {

        Optional<CountryEntity> countryEntity = StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .filter(entity -> country.equals(entity.getCountry())).findFirst();
        if(countryEntity.isEmpty()){
            log.error(String.format("UnknownCountryException: Country: %s not found.", country));
            throw new UnknownCountryException(String.format("Country: %s not found.", country));
        }
        return countryEntity.get();
    }

    @Override
    public Collection<City> getAllCities() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .map(cityEntity -> new City(
                        cityEntity.getCityId(),
                        cityEntity.getCity(),
                        cityEntity.getCountry().getCountry()
                )).collect(Collectors.toList());
    }

    @Override
    public void recordCity(City city) throws UnknownCountryException, InvalidCityException {
        CityEntity cityEntity;
        cityEntity = CityEntity.builder()
                .city(city.getCity())
                .country(queryCountry(city.getCountry()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CityEntity created: {}", cityEntity.toString());
        try{
            cityRepository.save(cityEntity);
        } catch (Exception e){
            log.error("Error while saving new City: " + e.getMessage());
            throw new InvalidCityException("Error while Saving City: " + city.getCity());
        }
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException, CityInUseException {
        Optional<CityEntity> cityEntity = Optional.ofNullable(cityRepository.findFirstByCity(city.getCity()));
        if(cityEntity.isEmpty()){
            log.error(String.format("UnknownCityException: City %s not found", city.getCity()));
            throw new UnknownCityException(String.format("City: %s not found.", city.getCity()));
        }
        try {
            log.info(String.format("City: %s deleted.", city.getCity()));
            cityRepository.delete(cityEntity.get());
        } catch (Exception e){
            log.error("Error while deleting city: " + e.getMessage());
            throw new CityInUseException("City: " + city.getCity() + " is used by another table");
        }
    }
    @Override
    public City readCityById(int cityId) throws UnknownCityException {
        Optional<CityEntity> cityEntity = cityRepository.findById(cityId);
        if(cityEntity.isEmpty()){
            log.error(String.format("UnknownStoreException: Store with id:%d not found.", cityId));
            throw new UnknownCityException(String.format("Store with id:%d not found.", cityId));
        }
        return new City(
                cityEntity.get().getCityId(),
                cityEntity.get().getCity(),
                cityEntity.get().getCountry().getCountry());
    }

    @Override
    public void updateCity(String cityName, City newCity) throws InvalidCityException, UnknownCityException, UnknownCountryException {
        Optional<CityEntity> cityEntity = Optional.ofNullable(cityRepository.findFirstByCity(cityName));
        if(cityEntity.isEmpty()){
            log.error(String.format("UnknownCityException: City %s not found", cityName));
            throw new UnknownCityException(String.format("City: %s not found.", cityName));
        }
        else {
            CityEntity newCityEntity = CityEntity.builder()
                    .cityId(cityEntity.get().getCityId())
                    .city(newCity.getCity())
                    .country(queryCountry(newCity.getCountry()))
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build();
            try{
                log.info("CityEntity Updated:" + newCityEntity.toString());
                cityRepository.save(newCityEntity);
            } catch (Exception e){
                log.error("Error while updating city: " + e.getMessage());
                throw new InvalidCityException("Cant create City with these parameters.");
            }
        }
    }
}