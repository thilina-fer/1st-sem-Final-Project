package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmpAttendanceDto {
    private String attId;
    private String empId;
    private String date;
    private String attendTime;
    private String duration;
}
