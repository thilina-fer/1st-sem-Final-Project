package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.EmployeeDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class     EmployeeModel {
    public boolean saveEmployee(EmployeeDto employeeDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute(
                "INSERT INTO Employee VALUES(?,?,?,?,?,?)",
                employeeDto.getEmpId(),
                employeeDto.getEmpName(),
                employeeDto.getEmpContact(),
                employeeDto.getEmpAddress(),
                employeeDto.getEmpAge(),
                employeeDto.getSalary()
        );
    }
    public boolean updateEmployee(EmployeeDto employeeDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute(
                "UPDATE Employee SET emp_name = ? , emp_contact = ? , emp_address = ? , emp_age = ? , salary = ? WHERE emp_id = ?",
                employeeDto.getEmpName(),
                employeeDto.getEmpContact(),
                employeeDto.getEmpAddress(),
                employeeDto.getEmpAge(),
                employeeDto.getSalary(),
                employeeDto.getEmpId()
        );
    }
    public boolean deleteEmployee(String empId) throws ClassNotFoundException ,SQLException{
        return CrudUtil.execute("DELETE FROM Employee WHERE emp_id = ?",
        empId);
    }
    public EmployeeDto searchEmployee(String empId) throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee WHERE emp_id = ?",
                empId);
        if (resultSet.next()){
            EmployeeDto dto = new EmployeeDto(
                    resultSet.getString("emp_id"),
                    resultSet.getString("emp_name"),
                    resultSet.getString("emp_contact"),
                    resultSet.getString("emp_attendance"),
                    resultSet.getInt("emp_age"),
                    resultSet.getDouble("salary")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<EmployeeDto> getAllEmployee() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee");
        ArrayList<EmployeeDto> employeeDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            );
            employeeDtoArrayList.add(employeeDto);
        }
        return employeeDtoArrayList;
    }
    public String getNextEmployeeId() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT emp_id FROM Customer ORDER BY emp_id DESC LIMIT 1");
        char tableChartacter = 'E';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d" , nextIdNumber);
            return nextIdString;
        }
        return tableChartacter + "001";
    }

}