package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.PreOrderDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreOrderModel {
    public boolean savePreOrder(PreOrderDto preOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Pre_Order_Manage VALUES(?,?,?,?)",
                preOrderDto.getPreOrderId(),
                preOrderDto.getUserId(),
                preOrderDto.getItemId(),
                preOrderDto.getAdvance()
                );
    }
    public boolean updatePreOrder(PreOrderDto preOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Pre_Order_Manage SET user_id = ? , item_id = ? , advance_payment = ? WHERE  pre_order_id = ? ",
                preOrderDto.getUserId(),
                preOrderDto.getItemId(),
                preOrderDto.getAdvance(),
                preOrderDto.getPreOrderId()
                );
    }
    public boolean deletePreOrder(String preOrderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE Pre_Order_Manage WHERE pre_order_id =  ? ",
                preOrderId);
    }
    public PreOrderDto searchPreOrder(String preOrderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Pre_Order_Manage",
                preOrderId);
        ArrayList<PreOrderDto> preOrderDtos = new ArrayList<>();
        while (resultSet.next()) {
            PreOrderDto dto = new PreOrderDto(
                    resultSet.getString("pre_order_id"),
                    resultSet.getString("user_id = ? "),
                    resultSet.getString("item_id"),
                    resultSet.getDouble("advance_payment")
            );
            return dto;
        }
        return null;
    }

}
