package lk.ijse.alphamodifications.model;


import lk.ijse.alphamodifications.dto.PurchasePaymentDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchasePaymentModel {
    public boolean savePurchasePay(PurchasePaymentDto purchasePaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_ Payment(?,?,?,?)",
                purchasePaymentDto.getPaymentId(),
                purchasePaymentDto.getOrderId(),
                purchasePaymentDto.getPaymentType(),
                purchasePaymentDto.getPayDayTime()
        );
    }

    public boolean updatePurchasePay(PurchasePaymentDto purchasePaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Payment SET order_id = ? , payment_type = ? , per_day_time = ? WHERE payment_id = ?",
                purchasePaymentDto.getOrderId(),
                purchasePaymentDto.getPaymentType(),
                purchasePaymentDto.getPayDayTime(),
                purchasePaymentDto.getPaymentId()
        );
    }
    public boolean deletePurchasePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Purchase_Payment WHERE payment_id = ? ",
        paymentId);
    }
    public PurchasePaymentDto searchPurchasePayment (String paymentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Payment WHERE payment_id  = ? ",
                paymentId);

        if (resultSet.next()){
            PurchasePaymentDto dto = new PurchasePaymentDto(
            resultSet.getString("payment_id"),
            resultSet.getString("order_id"),
            resultSet.getString("payment_type"),
            resultSet.getString("per_day_time")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<PurchasePaymentDto> getAllPurchasePayment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Payment");
        ArrayList<PurchasePaymentDto> purchasePaymentDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            PurchasePaymentDto dto = new PurchasePaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            purchasePaymentDtoArrayList.add(dto);
        }
        return purchasePaymentDtoArrayList;
    }
    public String getNextPurchasePaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT payment_id FROM Item ORDER BY payment_id DESC LIMIT 1");
        String tableString = "PP";

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
