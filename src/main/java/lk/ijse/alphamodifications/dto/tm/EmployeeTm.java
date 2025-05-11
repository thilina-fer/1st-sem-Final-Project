package lk.ijse.alphamodifications.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTm {
    private String employeeId;
    private String name;
    private String contact;
    private String address;
    private int age;
    private double salary;
}
