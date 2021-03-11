package inf.unideb.hu.dao.staff;

import inf.unideb.hu.dao.StaffEntity;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
}