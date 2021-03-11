package inf.unideb.hu.controller;

import inf.unideb.hu.exception.adress.UnknownAddressException;
import inf.unideb.hu.exception.staff.InvalidStaffException;
import inf.unideb.hu.exception.staff.StaffInUseException;
import inf.unideb.hu.exception.staff.UnknownStaffException;
import inf.unideb.hu.exception.store.UnknownStoreException;
import inf.unideb.hu.model.Staff;
import inf.unideb.hu.service.StaffService;
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
public class StaffController {

    private final StaffService staffService;

    @ApiOperation("Get staff members")
    @GetMapping("/staff")
    public Collection<StaffDto> getAllStaffMembers(){
        return staffService.readAll()
                .stream()
                .map(model -> StaffDto.builder()
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .address(model.getAddress())
                        .email(model.getEmail())
                        .storeAddress(model.getStoreAddress())
                        .build()
                ).collect(Collectors.toList());
    }

    @ApiOperation("Record staff member")
    @PostMapping("/staff")
    public void recordStaffMember(@RequestBody StaffRecordDto staffDto) {
        try {
            staffService.createStaffMember(new Staff(
                    staffDto.getFirstName(),
                    staffDto.getLastName(),
                    staffDto.getAddress(),
                    staffDto.getEmail(),
                    staffDto.getStoreAddress(),
                    staffDto.getUserName(),
                    staffDto.getPassword(),
                    staffDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException | InvalidStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Update staff member")
    @PutMapping("/staff")
    public void updateStaffMember(@RequestBody StaffUpdateDto staffUpdateDto){
        try {
            staffService.updateStaffMember(staffUpdateDto.getStaffId(), new Staff(
                    staffUpdateDto.getFirstName(),
                    staffUpdateDto.getLastName(),
                    staffUpdateDto.getAddress(),
                    staffUpdateDto.getEmail(),
                    staffUpdateDto.getStoreAddress(),
                    staffUpdateDto.getUserName(),
                    staffUpdateDto.getPassword(),
                    staffUpdateDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException | UnknownStaffException | InvalidStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation("Delete staff member")
    @DeleteMapping("/staff")
    public void deleteStaffMember(@RequestParam(name = "staffId", defaultValue = "1", required = true) int staffId){
        try {
            staffService.deleteStaffMember(staffId);
        } catch (UnknownStaffException | StaffInUseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}