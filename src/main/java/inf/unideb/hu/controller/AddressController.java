package inf.unideb.hu.controller;

import inf.unideb.hu.exception.adress.AddressInUseException;
import inf.unideb.hu.exception.adress.InvalidAddressException;
import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.city.UnknownCityException;
import inf.unideb.hu.model.Address;
import inf.unideb.hu.service.AddressService;
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
public class AddressController {

    private final AddressService addressService;

    @ApiOperation("Get addresses")
    @GetMapping("/address")
    public Collection<AddressDto> listAllAddresses(){
        return addressService.getAll()
                .stream()
                .map(model -> AddressDto.builder()
                        .address(model.getAddress())
                        .address2(model.getAddress2())
                        .district(model.getDistrict())
                        .cityName(model.getCityName())
                        .postalCode(model.getPostalCode())
                        .phone(model.getPhone())
                        .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation("Add Address")
    @PostMapping("/address")
    public void recordAddress(@RequestBody AddressDto addressDto){
        try {
            addressService.createAddress(new Address(
                    addressDto.getAddress(),
                    addressDto.getAddress2(),
                    addressDto.getDistrict(),
                    addressDto.getCityName(),
                    addressDto.getPostalCode(),
                    addressDto.getPhone()
            ));
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Delete address")
    @DeleteMapping("/address")
    public void deleteAddress(@RequestBody AddressDto addressDto){
        try {
            addressService.deleteAddress(new Address(
                    addressDto.getAddress(),
                    addressDto.getAddress2(),
                    addressDto.getDistrict(),
                    addressDto.getCityName(),
                    addressDto.getPostalCode(),
                    addressDto.getPhone()
            ));
        } catch (AddressInUseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnknownAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); }
    }

    @ApiOperation("Update address")
    @PutMapping("/address")
    public void updateAddress(@RequestBody AddressUpdateDto addressDto){
        try{
            addressService.updateAddress(addressDto.getOldAddress(),new Address(
                    addressDto.getAddress(),
                    addressDto.getAddress2(),
                    addressDto.getDistrict(),
                    addressDto.getCityName(),
                    addressDto.getPostalCode(),
                    addressDto.getPhone()
            ));
        } catch (UnknownAddressException | UnknownCityException | InvalidAddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}