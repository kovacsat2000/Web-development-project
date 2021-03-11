package inf.unideb.hu.dao.country;

import inf.unideb.hu.dao.CountryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
    CountryEntity findFirstByCountry(String country);
}