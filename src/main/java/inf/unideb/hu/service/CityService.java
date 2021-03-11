package inf.unideb.hu.service;

import inf.unideb.hu.exception.city.CityInUseException;
import inf.unideb.hu.exception.city.InvalidCityException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.City;

import java.util.Collection;

public interface CityService {

    Collection<City> getAllCities();
    void recordCity(City city) throws UnknownCountryException, InvalidCityException;
    void deleteCity(City city) throws UnknownCityException, CityInUseException;
    City readCityById(int cityId) throws UnknownCityException, CityInUseException;
    void updateCity(String cityName, City newCity) throws InvalidCityException, UnknownCityException, UnknownCountryException;
}