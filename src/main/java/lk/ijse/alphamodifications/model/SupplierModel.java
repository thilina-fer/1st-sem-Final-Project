package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.SupplierDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Supplier VALUES",
                supplierDto.getSupId(),
                supplierDto.getSupName(),
                supplierDto.getSupContact()
                );
    }
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Supplier SET supplier_name = ? , supplier_contact = ? WHERE supplier_id = ?",
                supplierDto.getSupName(),
                supplierDto.getSupContact(),
                supplierDto.getSupId()
        );
    }
    public boolean deleteSupplier(String sup_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE supplier_id = ?",
                sup_id);
    }
    public SupplierDto searchSupplier(String sup_id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Supplier",
                sup_id);
        if (resultSet.next()) {
            SupplierDto dto = new SupplierDto(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("supplier_contact")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Supplier");
        ArrayList<SupplierDto> supplierDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            SupplierDto dto = new SupplierDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            supplierDtoArrayList.add(dto);
        }
        return supplierDtoArrayList;
    }
    public String getNextSupplierId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");
        char tableChartacter = 'S';

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
