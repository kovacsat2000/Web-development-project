package inf.unideb.hu.dao.customer;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.customer.CustomerInUseException;
import inf.unideb.hu.exception.customer.InvalidCustomerException;
import inf.unideb.hu.exception.customer.UnknownCustomerException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Customer;

import java.util.Collection;

public interface CustomerDao {

    Collection<Customer> readAll();
    void createCustomer(Customer customer) throws UnknownAddressException, UnknownStoreException, InvalidCustomerException;
    void deleteCustomer(String email) throws UnknownCustomerException, CustomerInUseException;
    void updateCustomer(String email, Customer customer) throws UnknownCustomerException, UnknownAddressException, UnknownStoreException, InvalidCustomerException;
}