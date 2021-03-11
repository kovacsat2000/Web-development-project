package inf.unideb.hu.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffRecordDto extends StaffDto {

    private String userName;
    private String password;
    private int active;
}