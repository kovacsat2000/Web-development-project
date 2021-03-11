package inf.unideb.hu.service;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.store.InvalidStoreException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Store;

import java.util.Collection;

public interface StoreService {

    Collection<Store> readAll();
    Store readById(int storeId) throws UnknownStoreException;
    void updateStoreAddress(String currentAddress, String newAddress) throws UnknownAddressException, UnknownStoreException, InvalidStoreException;
}