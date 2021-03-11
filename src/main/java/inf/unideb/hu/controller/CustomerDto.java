package inf.unideb.hu.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String storeAddress;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int active;
}