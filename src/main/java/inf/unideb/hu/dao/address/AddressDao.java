package inf.unideb.hu.dao.address;

import inf.unideb.hu.exception.adress.AddressInUseException;
import inf.unideb.hu.exception.adress.InvalidAddressException;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.model.Address;

import java.util.Collection;

public interface AddressDao {

    Collection<Address> readAll();
    void createAddress(Address address) throws UnknownCityException;
    void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException;
    void updateAddress(String address, Address newAddress) throws UnknownAddressException, UnknownCityException, InvalidAddressException;
}