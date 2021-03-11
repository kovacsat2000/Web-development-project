package inf.unideb.hu.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String address;
    private String address2;
    private String district;
    private String cityName;
    private String postalCode;
    private String phone;
}