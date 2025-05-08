package lk.ijse.alphamodifications.model;

import lk.ijse.alphamodifications.dto.ItemDto;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public boolean saveItem(ItemDto itemDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute(
                "INSERT INTO Item VALUES(?,?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getQuantity(),
                itemDto.getBuyPrice(),
                itemDto.getSellPrice()
        );
    }
    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Item SET item_name = ? , quantity = ? , buying_price = ? , selling_price = ? WHERE item_id = ?",
                itemDto.getItemName(),
                itemDto.getQuantity(),
                itemDto.getBuyPrice(),
                itemDto.getSellPrice(),
                itemDto.getItemId()
        );
    }
    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE item_id ? ",
        itemId);
    }
    public ItemDto searchItem(String itemId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE item-id = ?",
        itemId);

        if(resultSet.next()){
            ItemDto dto = new ItemDto(
                    resultSet.getString("item_id"),
                    resultSet.getString("item_name"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("buying_price"),
                    resultSet.getDouble("selling_price")
            );
            return dto;
        }
        return null;
    }
    public ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<ItemDto> itemDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            ItemDto itemDto = new ItemDto(
            resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getInt(3),
            resultSet.getDouble(4),
            resultSet.getDouble(5)
            );
            itemDtoArrayList.add(itemDto);
        }
        return itemDtoArrayList;

    }public String getNextItemId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT item_id FROM Item ORDER BY item_id DESC LIMIT 1");
        char tableChartacter = 'I';

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

