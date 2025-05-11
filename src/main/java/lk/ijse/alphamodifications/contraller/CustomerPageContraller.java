package lk.ijse.alphamodifications.contraller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.alphamodifications.dto.CustomerDto;
import lk.ijse.alphamodifications.dto.tm.CustomerTm;
import lk.ijse.alphamodifications.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerPageContraller implements Initializable {
    public Label lblCustomerId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;

    public TableView<CustomerTm> tblCustomer;
    public TableColumn<CustomerTm, String > colId;
    public TableColumn<CustomerTm, String > colName;
    public TableColumn<CustomerTm, String > colContact;
    public TableColumn<CustomerTm, String > colAddress;

    private final CustomerModel customerModel = new CustomerModel();


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPattern = "^[A-Za-z ]+$";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            restPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
       tblCustomer.setItems(FXCollections.observableArrayList(
               customerModel.getAllCustomer().stream()
                       .map(customerDto -> new CustomerTm(
                               customerDto.getCustId(),
                               customerDto.getCustName(),
                               customerDto.getCustContact(),
                               customerDto.getCustAddresss()
                       )).toList()
       ));
    }

    private void restPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.setText("");
            txtContact.setText("");
            txtAddress.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"1 went wrong").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidAddress = address.matches(addressPattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0");

        if(!isValidName) txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        if (!isValidContact) txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        if (!isValidAddress) txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");

        CustomerDto customerDto = new CustomerDto(
                customerId,
                name,
                contact,
                address
        );

        if(isValidName && isValidContact && isValidAddress){
            try {
                boolean isSaved = customerModel.saveCustomer(customerDto);

                if(isSaved){
                    restPage();
                    new Alert(Alert.AlertType.INFORMATION,"Saved").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        CustomerDto customerDto = new CustomerDto(
                customerId,
                name,
                contact,
                address
        );

        try {
            boolean isUpdated = customerModel.updateCustomer(customerDto);

            if(isUpdated){
                restPage();
                new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO
                );

        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);
                if(isDeleted){
                    restPage();
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        }
    }
public void btnResetOnAction(ActionEvent actionEvent) {
        restPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }


    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomerId());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());
            txtAddress.setText(selectedItem.getAddress());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
