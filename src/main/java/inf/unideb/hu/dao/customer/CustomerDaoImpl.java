package inf.unideb.hu.dao.customer;

import inf.unideb.hu.dao.AddressEntity;
import inf.unideb.hu.dao.CustomerEntity;
import inf.unideb.hu.dao.StoreEntity;
import inf.unideb.hu.dao.address.AddressRepository;
import inf.unideb.hu.dao.store.StoreRepository;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.customer.CustomerInUseException;
import inf.unideb.hu.exception.customer.InvalidCustomerException;
import inf.unideb.hu.exception.customer.UnknownCustomerException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    public AddressEntity queryAddress(String addressName) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = Optional.ofNullable(
                addressRepository.findFirstByAddress(addressName));
        if(addressEntity.isEmpty()){
            log.error(String.format("UnknownAddressException: Address: %s not found", addressName));
            throw new UnknownAddressException(String.format
                    ("Address not found: %s", addressName));
        }
        return addressEntity.get();
    }

    public StoreEntity queryStore(String storeAddress) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = Optional.ofNullable(
                storeRepository.findFirstByAddress_Address(storeAddress));
        if(storeEntity.isEmpty()){
            log.error(String.format("UnknownStoreException: Store at address: %s not found", storeAddress));
            throw new UnknownStoreException(String.format
                    ("Store at address: %s not found.", storeAddress));
        }
        return storeEntity.get();
    }

    @Override
    public Collection<Customer> readAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(),false)
                .map(entity -> new Customer(
                        entity.getStore().getAddress().getAddress(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getAddress().getAddress(),
                        entity.getActive()

                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException, InvalidCustomerException {
        CustomerEntity customerEntity;
        customerEntity = CustomerEntity.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .active(customer.getActive())
                .address(queryAddress(customer.getAddress()))
                .store(queryStore(customer.getStoreAddress()))
                .createDate(new Timestamp((new Date()).getTime()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("New Customer added: {} {}", customer.getFirstName(),
                customer.getLastName());
        try {
            customerRepository.save(customerEntity);
        } catch (Exception e){
            log.error("Error while saving new Customer: " + e.getMessage());
            throw new InvalidCustomerException("Cant create Customer with these parameters.");
        }
    }

    @Override
    public void deleteCustomer(String email) throws UnknownCustomerException, CustomerInUseException {
        Optional<CustomerEntity> customerEntity = Optional.ofNullable
                (customerRepository.findFirstByEmail(email));
        if(customerEntity.isEmpty()){
            log.error(String.format("UnknownCustomerException: Customer with email: %s not found",email));
            throw new UnknownCustomerException(String.format
                    ("Customer with email: %s not found", email ));
        }
        try {
            customerRepository.delete(customerEntity.get());
            log.info(String.format("Customer with email: %s deleted.",email));
        } catch (Exception e){
            log.error("Error while deleting Customer: " + e.getMessage());
            throw new CustomerInUseException(String.format("Customer: %s is used by other tables.", email));
        }
    }

    @Override
    public void updateCustomer(String email, Customer customer) throws UnknownCustomerException, UnknownAddressException, UnknownStoreException, InvalidCustomerException {
        Optional<CustomerEntity> customerEntity = Optional.ofNullable
                (customerRepository.findFirstByEmail(email));
        if(customerEntity.isEmpty()){
            log.error(String.format("UnknownCustomerException: Customer with email: %s not found",email));
            throw new UnknownCustomerException(String.format
                    ("Customer with email: %s not found", email ));
        }
        CustomerEntity newCustomerEntity;
        newCustomerEntity = CustomerEntity.builder()
                .customerId(customerEntity.get().getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .active(customer.getActive())
                .address(queryAddress(customer.getAddress()))
                .store(queryStore(customer.getStoreAddress()))
                .createDate(customerEntity.get().getCreateDate())
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("Customer Updated: {} {}", customer.getFirstName(),
                customer.getLastName());
        try {
            customerRepository.save(newCustomerEntity);
        } catch (Exception e){
            log.error("Error while saving new Customer: " + e.getMessage());
            throw new InvalidCustomerException("Cant create Customer with these parameters.");
        }
    }

}