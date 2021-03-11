package inf.unideb.hu.dao.address;

import inf.unideb.hu.dao.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    AddressEntity findFirstByAddress(String address);
}