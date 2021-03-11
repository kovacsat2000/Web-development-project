package inf.unideb.hu.service;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.staff.InvalidStaffException;
import inf.unideb.hu.exception.staff.StaffInUseException;
import inf.unideb.hu.exception.staff.UnknownStaffException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> readAll();
    void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException, InvalidStaffException;
    void updateStaffMember(int staffId, Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException, InvalidStaffException;
    void deleteStaffMember(int staffId) throws StaffInUseException, UnknownStaffException;
}