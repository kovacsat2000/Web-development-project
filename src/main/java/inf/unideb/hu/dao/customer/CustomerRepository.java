package inf.unideb.hu.dao.customer;

import inf.unideb.hu.dao.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    CustomerEntity findFirstByEmail(String email);
}