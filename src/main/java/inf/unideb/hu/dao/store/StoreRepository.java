package inf.unideb.hu.dao.store;

import inf.unideb.hu.dao.StoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {
    StoreEntity findFirstByAddress_Address(String storeAddress);
}