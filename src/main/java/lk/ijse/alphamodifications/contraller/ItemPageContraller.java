package lk.ijse.alphamodifications.contraller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.alphamodifications.dto.ItemDto;
import lk.ijse.alphamodifications.dto.tm.ItemTm;
import lk.ijse.alphamodifications.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemPageContraller implements Initializable {
    public Label lblItemId;
    public TextField txtName;
    public TextField txtQuantity;
    public TextField txtBuyingPrice;
    public TextField txtSellingPrice;


    public TableView<ItemTm> tblItem;
    public TableColumn<ItemTm , String> colId;
    public TableColumn<ItemTm , String> colName;
    public TableColumn<ItemTm , Integer> colQty;
    public TableColumn<ItemTm , Double> colBuyPrice;
    public TableColumn<ItemTm , Double> colSellPrice;

    private final ItemModel itemModel = new ItemModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String quantityPattern = "^\\d+$";
    private final String buyingPricePattern = "^\\d+(\\.\\d{1,2})?$";
    private final String sellingPricePattern = "^\\d+(\\.\\d{1,2})?$";

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colBuyPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblItem.setItems(FXCollections.observableArrayList(
                itemModel.getAllItem().stream()
                        .map(itemDto -> new ItemTm(
                                itemDto.getItemId(),
                                itemDto.getItemName(),
                                itemDto.getQuantity(),
                                itemDto.getBuyPrice(),
                                itemDto.getSellPrice()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText("");
            txtQuantity.setText("");
            txtBuyingPrice.setText("");
            txtSellingPrice.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        String qty = txtQuantity.getText();
        String buyingPrice = txtBuyingPrice.getText();
        String sellingPrice = txtSellingPrice.getText();

        boolean isValidName = itemName.matches(namePattern);
        boolean isValidQuantity = qty.matches(quantityPattern);
        boolean isValidBuyPrice = buyingPrice.matches(buyingPricePattern);
        boolean isValidSellPrice = sellingPrice.matches(sellingPricePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0");
        txtBuyingPrice.setStyle(txtBuyingPrice.getStyle() + ";-fx-border-color: #7367F0");
        txtSellingPrice.setStyle(txtSellingPrice.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidName) txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        if (!isValidQuantity) txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        if (!isValidBuyPrice) txtBuyingPrice.setStyle(txtBuyingPrice.getStyle() + ";-fx-border-color: red;");
        if (!isValidSellPrice) txtSellingPrice.setStyle(txtSellingPrice.getStyle() + ";-fx-border-color: red;");

        int presedQuantity = Integer.parseInt(qty);
        double presedBuyPrice = Double.parseDouble(buyingPrice);
        double presedSellPrice = Double.parseDouble(sellingPrice);

        ItemDto itemDto = new ItemDto(
                itemId,
                itemName,
                presedQuantity,
                presedBuyPrice,
                presedSellPrice
        );

        if (isValidName && isValidQuantity && isValidBuyPrice && isValidSellPrice){
            try {
                boolean isSaved = itemModel.saveItem(itemDto);

                if (isSaved){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        String qty = txtQuantity.getText();
        String buyingPrice = txtBuyingPrice.getText();
        String sellingPrice = txtSellingPrice.getText();

        int presedQuantity = Integer.parseInt(qty);
        double presedBuyPrice = Double.parseDouble(buyingPrice);
        double presedSellPrice = Double.parseDouble(sellingPrice);

        ItemDto itemDto = new ItemDto(
                itemId,
                itemName,
                presedQuantity,
                presedBuyPrice,
                presedSellPrice
        );

            try {
                boolean isUpdated = itemModel.saveItem(itemDto);

                if (isUpdated){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure ? ",
                ButtonType.YES,
                ButtonType.NO
                );

        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES){
            String itemId = lblItemId.getText();

            try {
                boolean isDeleted = itemModel.deleteItem(itemId);
                if (isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    private void loadNextId() throws ClassNotFoundException , SQLException{
        String nextId = String.valueOf(itemModel.getAllItem());
        lblItemId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblItemId.setText(selectedItem.getItemId());
            txtName.setText(selectedItem.getName());
            //txtQuantity.setText(selectedItem.getItemQuantity());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);

        }
    }
}

