package inf.unideb.hu.service;

import inf.unideb.hu.exception.adress.AddressInUseException;
import inf.unideb.hu.exception.adress.InvalidAddressException;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.model.Address;

import java.util.Collection;

public interface AddressService {

    Collection<Address> getAll();
    void createAddress(Address address) throws UnknownCityException;
    void deleteAddress(Address address) throws UnknownAddressException, AddressInUseException;
    void updateAddress(String addressName, Address newAddress) throws UnknownAddressException, UnknownCityException, InvalidAddressException;
}