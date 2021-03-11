package inf.unideb.hu.service;

import inf.unideb.hu.dao.country.CountryDao;
import inf.unideb.hu.exception.country.CountryInUseException;
import inf.unideb.hu.exception.country.InvalidCountryException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;

    @Override
    public Collection<Country> getAllCountries() {
        return countryDao.readAll();
    }

    @Override
    public void recordCounty(Country country) throws InvalidCountryException {
        countryDao.createCountry(country);
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException {
        countryDao.deleteCountry(country);
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException, InvalidCountryException {
        countryDao.updateCountry(country,newCountry);
    }
}