package inf.unideb.hu.service;

import inf.unideb.hu.exception.country.CountryInUseException;
import inf.unideb.hu.exception.country.InvalidCountryException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.Country;

import java.util.Collection;

public interface CountryService {

    Collection<Country> getAllCountries();
    void recordCounty(Country country) throws InvalidCountryException;
    void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException;
    void updateCountry(Country country, Country newCountry) throws UnknownCountryException, InvalidCountryException;
}