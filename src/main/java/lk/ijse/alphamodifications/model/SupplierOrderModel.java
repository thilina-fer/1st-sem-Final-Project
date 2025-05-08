package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.SupplierOrderDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {
    public boolean saveSuppilerOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Supplier_Order VALUES(?,?,?,?,?)",
                supplierOrderDto.getSoId(),
                supplierOrderDto.getSupId(),
                supplierOrderDto.getUserId(),
                supplierOrderDto.getDate(),
                supplierOrderDto.getItemId()
                );
    }
    public boolean updateSuppilerOrder(SupplierOrderDto supplierOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Supplier_Order SET supplier_id = ? , user_id = ? , date = ? , item_id = ? WHERE so_id = ?",
                supplierOrderDto.getSupId(),
                supplierOrderDto.getUserId(),
                supplierOrderDto.getDate(),
                supplierOrderDto.getItemId(),
                supplierOrderDto.getSoId()
        );
    }
    public boolean deleteSuppilerOrder(String soId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Supplier_Order WHERE so_id = ?",
                soId);
    }
    public SupplierOrderDto searchSuppilerOrder(String soId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet =  CrudUtil.execute("SELECT * FROM Supplier_Order WHERE so_id = ?",
                soId);
        if (resultSet.next()) {
            SupplierOrderDto dto = new SupplierOrderDto(
                    resultSet.getString("so_id"),
                    resultSet.getString("supplier_id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("date"),
                    resultSet.getString("item_id")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<SupplierOrderDto> getAllSuppilerOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Supplier_Order");
        ArrayList<SupplierOrderDto> supplierOrderDtoArrayList= new ArrayList<>();
        while (resultSet.next()) {
            SupplierOrderDto dto = new SupplierOrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            supplierOrderDtoArrayList.add(dto);
        }
        return supplierOrderDtoArrayList;
    }
    public String getNextSoId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
       String  tableString = "SO";

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
