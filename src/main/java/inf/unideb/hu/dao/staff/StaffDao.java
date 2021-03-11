package inf.unideb.hu.dao.staff;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.staff.InvalidStaffException;
import inf.unideb.hu.exception.staff.StaffInUseException;
import inf.unideb.hu.exception.staff.UnknownStaffException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Staff;

import java.util.Collection;

public interface StaffDao {

    Collection<Staff> readAll();
    void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException, InvalidStaffException;
    void updateStaffMember(int staffId, Staff staff) throws UnknownStaffException, UnknownAddressException, UnknownStoreException, InvalidStaffException;
    void deleteStaffMember(int staffId) throws UnknownStaffException, StaffInUseException;
}