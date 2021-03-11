package inf.unideb.hu.dao.store;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.store.InvalidStoreException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Store;

import java.util.Collection;

public interface StoreDao {

    Collection<Store> readAll();
    Store readById(int storeId) throws UnknownStoreException;
    void updateStoreAddress(String currentAddress, String newAddress) throws UnknownStoreException, UnknownAddressException, InvalidStoreException;
}