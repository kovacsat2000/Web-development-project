package inf.unideb.hu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Address {

    private String address;
    private String address2;
    private String district;
    private String cityName;
    private String postalCode;
    private String phone;
}