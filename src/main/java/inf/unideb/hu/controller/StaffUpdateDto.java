package inf.unideb.hu.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffUpdateDto extends StaffRecordDto {

    private int staffId;
}