package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.OrderPaymentDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderPaymentModel {
    public boolean saveOrderPayment(OrderPaymentDto orderPaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Order_Payment VALUES(?,?,?,?,?)",
                orderPaymentDto.getOpId(),
                orderPaymentDto.getSupId(),
                orderPaymentDto.getSoId(),
                orderPaymentDto.getAmount(),
                orderPaymentDto.getPayType()
                );
    }
    public boolean updateOrderPayment(OrderPaymentDto orderPaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Order_Payment SET supplier_id = ? , so_id = ? , amount = ? , op_pay_type = ? WHERE op_id = ?",
                orderPaymentDto.getSupId(),
                orderPaymentDto.getSoId(),
                orderPaymentDto.getAmount(),
                orderPaymentDto.getPayType(),
                orderPaymentDto.getOpId()
                );
    }
    public boolean deleteOrderPayment(String opId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Order_Payment WHERE op_id = ?",
                opId);
    }
    public OrderPaymentDto serchOrderPayment(String opId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Order_Payment SET supplier_id = ? , so_id = ? , amount = ? , op_pay_type = ? WHERE op_id = ? ");
        if (resultSet.next()) {
            OrderPaymentDto dto = new OrderPaymentDto(
                    resultSet.getString("op_id"),
                    resultSet.getString("supplier_id"),
                    resultSet.getString("so_id"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("op_pay_type")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<OrderPaymentDto> getAllOrderPayment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Order_Payment ");
        ArrayList<OrderPaymentDto> orderPaymentDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OrderPaymentDto dto = new OrderPaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            orderPaymentDtoArrayList.add(dto);
        }
        return orderPaymentDtoArrayList;
    }
    public String getNextPreOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT Pre_Order_Manage FROM Item ORDER BY Pre_Order_Manage DESC LIMIT 1");
        String tableString = "PR";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableString + "001";
    }

}
