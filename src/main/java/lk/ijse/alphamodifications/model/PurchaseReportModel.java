package lk.ijse.alphamodifications.model;


import lk.ijse.alphamodifications.dto.PurchaseReportDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseReportModel {
    public boolean saveReport(PurchaseReportDto purchaseReportDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_Report VALUES(?,?,?)",
                purchaseReportDto.getReportId(),
                purchaseReportDto.getOrderId(),
                purchaseReportDto.getDescription()
                );
    }
    public boolean updateReport(PurchaseReportDto purchaseReportDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Report SET order_id = ?, description = ? WHERE report_id = ?",
                purchaseReportDto.getOrderId(),
                purchaseReportDto.getDescription(),
                purchaseReportDto.getReportId()
        );
    }
    public boolean deleteReport(String reportId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Purchase_Report WHERE report_id = ?",
                reportId);
    }
    public PurchaseReportDto searchReport(String reportId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Report WHERE report_id = ?",
                reportId);
        if (resultSet.next()) {
            PurchaseReportDto dto = new PurchaseReportDto(
                    resultSet.getString("report_id "),
                    resultSet.getString("order_id"),
                    resultSet.getString("description")
            );
            return dto;
        }
        return null;
    }
    public String getNextReportId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT item_id FROM Item ORDER BY item_id DESC LIMIT 1");
        char tableChartacter = 'R';

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
