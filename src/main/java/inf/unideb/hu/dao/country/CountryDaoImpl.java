package inf.unideb.hu.dao.country;

import inf.unideb.hu.dao.CountryEntity;
import inf.unideb.hu.exception.country.CountryInUseException;
import inf.unideb.hu.exception.country.InvalidCountryException;
import inf.unideb.hu.exception.country.UnknownCountryException;
import inf.unideb.hu.model.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryDaoImpl implements CountryDao {

    private final CountryRepository countryRepository;



    @Override
    public Collection<Country> readAll() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .map(entity -> new Country(
                        entity.getCountry()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createCountry(Country country) throws InvalidCountryException {
        CountryEntity countryEntity;

        countryEntity = CountryEntity.builder()
                .country(country.getCountry())
                .lastUpdate(new Timestamp(new Date().getTime()))
                .build();
        log.info("Created CountryEntity: {}",countryEntity);
        try{
            countryRepository.save(countryEntity);
        } catch (Exception e){
            log.error("Country save error: " + e.getMessage());
            throw new InvalidCountryException("Cant create Country with these parameters.");
        }
    }

    @Override
    public void deleteCountry(Country country) throws UnknownCountryException, CountryInUseException {
        Optional<CountryEntity> countryEntity = Optional.
                ofNullable(countryRepository.findFirstByCountry(country.getCountry()));
        if(countryEntity.isEmpty()){
            log.error(String.format("UnknownCountryException: Can't find country: %s", country));
            throw new UnknownCountryException(String.format("Country not found: %s", country));
        }
        try {
            log.info(String.format("Country %s successfully deleted.", country.getCountry()));
            countryRepository.delete(countryEntity.get());
        } catch (Exception e) {
            log.error(String.format("Error: Country %s is in use by a table.", country.getCountry()));
            throw new CountryInUseException(String.format("Country %s is used by another table.", country.getCountry()));
        }
    }

    @Override
    public void updateCountry(Country country, Country newCountry) throws UnknownCountryException, InvalidCountryException {
        Optional<CountryEntity> countryEntity = Optional.ofNullable(countryRepository.findFirstByCountry(country.getCountry()));
        if(countryEntity.isEmpty()){
            log.error(String.format("UnknownCountryException: Can't find country: %s", country.getCountry()));
            throw new UnknownCountryException(String.format("Country not found: %s", country.getCountry()));
        }
        else {
            CountryEntity newCountryEntity = CountryEntity.builder()
                    .countryId(countryEntity.get().getCountryId())
                    .country(newCountry.getCountry())
                    .lastUpdate(new Timestamp(new Date().getTime()))
                    .build();
            log.info("Created CountryEntity: {}", countryEntity.get());
            try {
                countryRepository.save(newCountryEntity);
            } catch (Exception e) {
                log.error("Country save error: " + e.getMessage());
                throw new InvalidCountryException("Cant create Country with these parameters.");
            }
        }
    }
}