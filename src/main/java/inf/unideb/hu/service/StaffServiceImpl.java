package inf.unideb.hu.service;

import inf.unideb.hu.dao.staff.StaffDao;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.staff.InvalidStaffException;
import inf.unideb.hu.exception.staff.StaffInUseException;
import inf.unideb.hu.exception.staff.UnknownStaffException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffDao staffDao;

    @Override
    public Collection<Staff> readAll() {
        return staffDao.readAll();
    }

    @Override
    public void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException, InvalidStaffException {
        staffDao.createStaffMember(staff);
    }

    @Override
    public void updateStaffMember(int staffId, Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException, InvalidStaffException {
        staffDao.updateStaffMember(staffId,staff);
    }

    @Override
    public void deleteStaffMember(int staffId) throws StaffInUseException, UnknownStaffException {
        staffDao.deleteStaffMember(staffId);
    }
}