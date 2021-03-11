package inf.unideb.hu.service;

import inf.unideb.hu.dao.address.AddressDao;
import inf.unideb.hu.exception.adress.AddressInUseException;
import inf.unideb.hu.exception.adress.InvalidAddressException;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.model.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public Collection<Address> getAll() {
        return addressDao.readAll();
    }

    @Override
    public void createAddress(Address address) throws UnknownCityException {
        addressDao.createAddress(address);
    }

    @Override
    public void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException {
        addressDao.deleteAddress(address);
    }

    @Override
    public void updateAddress(String addressName, Address newAddress) throws UnknownAddressException, UnknownCityException, InvalidAddressException {
        addressDao.updateAddress(addressName, newAddress);
    }
}