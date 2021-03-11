package inf.unideb.hu.service;

import inf.unideb.hu.dao.customer.CustomerDao;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.customer.CustomerInUseException;
import inf.unideb.hu.exception.customer.InvalidCustomerException;
import inf.unideb.hu.exception.customer.UnknownCustomerException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    public Collection<Customer> getAllCustomers() {
        return customerDao.readAll();
    }

    @Override
    public void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException, InvalidCustomerException {
        customerDao.createCustomer(customer);
    }

    @Override
    public void deleteCustomer(String email) throws UnknownCustomerException, CustomerInUseException {
        customerDao.deleteCustomer(email);
    }

    @Override
    public void updateCustomer(String email, Customer customer) throws UnknownCustomerException, UnknownStoreException, UnknownAddressException, InvalidCustomerException {
        customerDao.updateCustomer(email,customer);
    }

}