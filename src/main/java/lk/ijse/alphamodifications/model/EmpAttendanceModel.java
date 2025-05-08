package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.EmpAttendanceDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpAttendanceModel {
    public boolean saveAttendance(EmpAttendanceDto empAttendanceDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute("INSERT INTO Employee_Attendance VALUES(?,?,?,?,?)",
                empAttendanceDto.getAttId(),
                empAttendanceDto.getEmpId(),
                empAttendanceDto.getDate(),
                empAttendanceDto.getAttendTime(),
                empAttendanceDto.getDuration()

        );
    }
    public boolean updateAttendance(EmpAttendanceDto empAttendanceDto) throws ClassNotFoundException , SQLException {
        return CrudUtil.execute("UPDATE Emloyee_Attendance SET employee_id = ? , date = ? , attend_time = ? , duration = ? WHERE attendance_id = ? ",
                empAttendanceDto.getEmpId(),
                empAttendanceDto.getDate(),
                empAttendanceDto.getAttendTime(),
                empAttendanceDto.getDuration(),
                empAttendanceDto.getAttId()
                );
    }
    public boolean deleteAttendance(String attId) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute("DELETE FROM Employee_Attendance WHERE attendance_id = ? ",
        attId
        );
}
public EmpAttendanceDto searchAttendance(String attId) throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee_Attendance WHERE attendance_id = ?",
        attId
        );
        if(resultSet.next()){
            EmpAttendanceDto dto = new EmpAttendanceDto(
                    resultSet.getString("attendance_id"),
                    resultSet.getString("employee_name"),
                    resultSet.getString("date"),
                    resultSet.getString("attend_time"),
                    resultSet.getString("duration")
            );
            return dto;
        }
        return null;
    }

    public ArrayList<EmpAttendanceDto> getAllAttendance() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee_Attendance");
        ArrayList<EmpAttendanceDto> empAttendanceDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            EmpAttendanceDto dto = new EmpAttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            empAttendanceDtoArrayList.add(dto);
        }
        return empAttendanceDtoArrayList;
    }
    public String getNextAttendanceId() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT attendance_id FROM Employee_Attendance ORDERD BY attendance_id DESC LIMIT 1");
        char tableCharacter = 'A';
        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
