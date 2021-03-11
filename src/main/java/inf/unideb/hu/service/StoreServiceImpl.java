package inf.unideb.hu.service;

import inf.unideb.hu.dao.store.StoreDao;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.store.InvalidStoreException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;

    @Override
    public Collection<Store> readAll() {
        return storeDao.readAll();
    }

    @Override
    public Store readById(int storeId) throws UnknownStoreException {
        return storeDao.readById(storeId);
    }

    @Override
    public void updateStoreAddress(String currentAddress, String newAddress) throws UnknownAddressException, UnknownStoreException, InvalidStoreException {
        storeDao.updateStoreAddress(currentAddress,newAddress);
    }
}