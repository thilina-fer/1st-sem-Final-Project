package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.PurchaseOrderDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderModel {
    public boolean savePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_Order VALUES(?,?,?,?)",
                purchaseOrderDto.getOrderId(),
                purchaseOrderDto.getCustId(),
                purchaseOrderDto.getOrderDate(),
                purchaseOrderDto.getTotAmount()
                );
    }
    public boolean updatePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Order SET customer_id = ? , order_date = ? , total_amount = ? WHERE order_id = ?",
                purchaseOrderDto.getCustId(),
                purchaseOrderDto.getOrderDate(),
                purchaseOrderDto.getTotAmount(),
                purchaseOrderDto.getOrderId()
        );
    }
    public boolean deletePurchaseOrder(String orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Purchase_Order WHERE order_id = ?",
                orderId);
    }
    public PurchaseOrderDto searchPurchaseOrder(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Order",
                orderId);
        if (resultSet.next()) {
            PurchaseOrderDto dto = new PurchaseOrderDto(
                    resultSet.getString("order_id"),
                    resultSet.getString("customer_id"),
                    resultSet.getString("order_date"),
                    resultSet.getDouble("total_amount")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<PurchaseOrderDto> getAllPurchaseOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Order");
        ArrayList<PurchaseOrderDto> purchaseOrderDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            PurchaseOrderDto dto = new PurchaseOrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
            purchaseOrderDtoArrayList.add(dto);
        }
        return purchaseOrderDtoArrayList;
    }
    public String getNextPurchaseOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT order_id FROM Item ORDER BY order_id DESC LIMIT 1");
        char tableChartacter = 'O';

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
