package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeDto {
    private String empId;
    private String empName;
    private String empContact;
    private String empAddress;
    private int empAge;
    private double salary;
}
