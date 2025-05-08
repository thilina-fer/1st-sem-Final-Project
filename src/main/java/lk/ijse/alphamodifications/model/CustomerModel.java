package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.CustomerDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException , ClassNotFoundException{
        return CrudUtil.execute(
                "INSERT INTO Customer VALUES(?,?,?,?)",
                customerDto.getCustId(),
                customerDto.getCustName(),
                customerDto.getCustContact(),
                customerDto.getCustAddresss()
        );
    }
    public boolean updateCustomer(CustomerDto customerDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute(
                "UPDATE Customer SET customer_name = ? , customer_contact = ? , customer_address = ? WHERE customer_id = ?",
                customerDto.getCustName(),
                customerDto.getCustContact(),
                customerDto.getCustAddresss(),
                customerDto.getCustId()
        );
    }
    public boolean deleteCustomer(String custId) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute("DELETE FROM Customer WHERE customer_id = ?",
                custId
                );
    }
    public CustomerDto searchCustomer(String custId) throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE customer_id = ?",
                custId
                );
        if(resultSet.next()){
            CustomerDto dto = new CustomerDto(
                    resultSet.getString("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("customer_contact"),
                    resultSet.getString("customer_address")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<String> getAllCustomer() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<String> customerDtoArrayList = new ArrayList<>();

        while(resultSet.next()){
            String id = resultSet.getString(1);
            customerDtoArrayList.add(id);
        }
        return customerDtoArrayList;
    }

    public String getNextCustomerId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
        char tableChartacter = 'C';

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
