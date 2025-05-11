package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.UserDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public static boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO User VALUES(?,?,?,?,?,?,?)",
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getContact(),
                userDto.getAddress(),
                userDto.getRole()
                );
    }
    public boolean updateUser(UserDto userdto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE User SET user_name = ? , email = ? , password = ? , contact = ? , address = ? , role = ? WHERE user_id = ?",
                userdto.getUserName(),
                userdto.getEmail(),
                userdto.getPassword(),
                userdto.getContact(),
                userdto.getAddress(),
                userdto.getRole(),
                userdto.getUserId()
        );
    }
    public boolean deleteUser(String  userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE user_id = ?"
                ,userId);
    }
    public UserDto searchUser(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE user_id = ?",
                userId);
        if (resultSet.next()) {
            UserDto dto = new UserDto(
                    resultSet.getString("user_id"),
                    resultSet.getString("user_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("role")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<UserDto> searchAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User");
        ArrayList<UserDto> userDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            UserDto dto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            userDtoArrayList.add(dto);
        }
        return userDtoArrayList;
    }
    public static String getNextUserId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
        char tableChartacter = 'U';

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
