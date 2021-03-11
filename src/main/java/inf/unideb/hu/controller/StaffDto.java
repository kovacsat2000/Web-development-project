package inf.unideb.hu.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String storeAddress;
}