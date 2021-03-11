package inf.unideb.hu.controller;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.store.InvalidStoreException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Store;
import inf.unideb.hu.service.StoreService;
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
public class StoreController {

    private final StoreService storeService;

    @ApiOperation("Get stores")
    @GetMapping("/stores")
    public Collection<StoreDto> getAllStores(){
        return storeService.readAll()
                .stream().map(
                        model -> StoreDto.builder()
                                .staffEmail(model.getStaffEmail())
                                .storeAddress(model.getStoreAddress())
                                .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation("Get store by id")
    @GetMapping("/store")
    public StoreDto getStoreById(@RequestParam(name = "storeId", defaultValue = "1", required = true) int storeId){
        try {
            Store store = storeService.readById(storeId);
            return new StoreDto(store.getStaffEmail(),store.getStoreAddress());
        } catch (UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Update store address")
    @PostMapping("/store")
    public void updateStoreAddress(@RequestBody StoreUpdateDto storeUpdateDto){
        try {
            storeService.updateStoreAddress(storeUpdateDto.getAddress(),storeUpdateDto.getNewAddress());
        } catch (UnknownAddressException | UnknownStoreException | InvalidStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}