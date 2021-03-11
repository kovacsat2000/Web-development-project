package inf.unideb.hu.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Staff {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String storeAddress;
    private String userName;
    private String password;
    private int active;
}