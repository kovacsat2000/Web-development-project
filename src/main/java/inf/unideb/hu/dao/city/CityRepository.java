package inf.unideb.hu.dao.city;

import inf.unideb.hu.dao.CityEntity;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    CityEntity findFirstByCity(String city);
}