package inf.unideb.hu.controller;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.customer.CustomerInUseException;
import inf.unideb.hu.exception.customer.InvalidCustomerException;
import inf.unideb.hu.exception.customer.UnknownCustomerException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Customer;
import inf.unideb.hu.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService service;

    @ApiOperation("Get customers")
    @GetMapping("/customer")
    public Collection<CustomerDto> listAllCustomers(){
        return service.getAllCustomers()
                .stream()
                .map(model -> CustomerDto.builder()
                        .storeAddress(model.getStoreAddress())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .address(model.getAddress())
                        .email(model.getEmail())
                        .active(model.getActive())
                        .build())
                .collect(Collectors.toList());
    }

    @ApiOperation("Create customer")
    @PostMapping("/customer")
    public void createCustomer(@RequestBody CustomerDto customerDto){
        try {
            service.createCustomer(new Customer(
                    customerDto.getStoreAddress(),
                    customerDto.getFirstName(),
                    customerDto.getLastName(),
                    customerDto.getEmail(),
                    customerDto.getAddress(),
                    customerDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException | InvalidCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Delete customer")
    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestParam(name = "email", defaultValue = "example@test.com", required = true) String email){
        try {
            service.deleteCustomer(email);
        } catch (UnknownCustomerException | CustomerInUseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Update customer")
    @PutMapping("/customer")
    public void updateCustomer(@RequestBody CustomerUpdateDto customerUpdateDto){
        try {
            service.updateCustomer(customerUpdateDto.getCurrentEmail(), new Customer(
                    customerUpdateDto.getStoreAddress(),
                    customerUpdateDto.getFirstName(),
                    customerUpdateDto.getLastName(),
                    customerUpdateDto.getEmail(),
                    customerUpdateDto.getAddress(),
                    customerUpdateDto.getActive()
            ));
        } catch (UnknownCustomerException | UnknownStoreException | UnknownAddressException | InvalidCustomerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}