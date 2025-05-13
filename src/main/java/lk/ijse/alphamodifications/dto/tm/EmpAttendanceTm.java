package lk.ijse.alphamodifications.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmpAttendanceTm {
    private String attendanceId;
    private String empId;
    private String date;
    private String attendTime;
    private String duration;

}
