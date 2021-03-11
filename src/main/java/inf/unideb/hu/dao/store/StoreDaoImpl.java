package inf.unideb.hu.dao.store;

import inf.unideb.hu.dao.AddressEntity;
import inf.unideb.hu.dao.StoreEntity;
import inf.unideb.hu.dao.address.AddressRepository;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.store.InvalidStoreException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreDaoImpl implements StoreDao {

    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;

    public AddressEntity queryAddress(String addressName) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = Optional.ofNullable(
                addressRepository.findFirstByAddress(addressName));
        if(addressEntity.isEmpty()){
            log.error(String.format("UnknownAddressException: Address not found: %s", addressName));
            throw new UnknownAddressException(String.format
                    ("Address not found: %s", addressName));
        }
        return addressEntity.get();
    }

    @Override
    public Collection<Store> readAll() {
        return StreamSupport.stream(storeRepository.findAll().spliterator(), false)
                .map(storeEntity -> new Store(
                        storeEntity.getStaff().getEmail(),
                        storeEntity.getAddress().getAddress()
                )).collect(Collectors.toList());
    }

    @Override
    public Store readById(int storeId) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);
        if(storeEntity.isEmpty()){
            log.error(String.format("UnknownStoreException: Store with id:%d not found.", storeId));
            throw new UnknownStoreException(String.format("Store with id:%d not found.", storeId));
        }
        return new Store(
                storeEntity.get().getStaff().getEmail(),
                storeEntity.get().getAddress().getAddress());
    }

    @Override
    public void updateStoreAddress(String currentAddress, String newAddress) throws UnknownStoreException, UnknownAddressException, InvalidStoreException {
        Optional<StoreEntity> storeEntity = Optional.ofNullable(
                storeRepository.findFirstByAddress_Address(currentAddress));
        if(storeEntity.isEmpty()) {
            log.error(String.format("UnknownStoreException: Store at %s not found.", currentAddress));
            throw new UnknownStoreException(String.format("Store at %s not found.", currentAddress));
        }
        else {
            StoreEntity newStoreEntity = StoreEntity.builder()
                    .storeId(storeEntity.get().getStoreId())
                    .address(queryAddress(newAddress))
                    .staff(storeEntity.get().getStaff())
                    .lastUpdate(new Timestamp(new Date().getTime()))
                    .build();
            try {
                storeRepository.save(newStoreEntity);
                log.info(String.format("Store with id %d updated.", storeEntity.get().getStoreId()));
            } catch (Exception e){
                log.error("Error while updating Store Address: " + e.getMessage());
                throw new InvalidStoreException("Cant update Store with these parameters");
            }
        }
    }
}