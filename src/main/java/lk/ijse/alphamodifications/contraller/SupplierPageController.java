package lk.ijse.alphamodifications.contraller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.alphamodifications.dto.SupplierDto;
import lk.ijse.alphamodifications.dto.tm.SupplierTm;
import lk.ijse.alphamodifications.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {
    public Label lblSupplierId;
    public TextField txtName;
    public TextField txtContact;


    public TableView<SupplierTm> tblSupplier;
    public TableColumn<SupplierTm, String> colId;
    public TableColumn<SupplierTm, String> ColName;
    public TableColumn<SupplierTm, String> ColContact;

    private final SupplierModel supplierModel = new SupplierModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";


    public void initialize(URL url, ResourceBundle resource) {
        colId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        ColContact.setCellValueFactory(new PropertyValueFactory<>("SupplierContact"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblSupplier.setItems(FXCollections.observableArrayList(
                supplierModel.getAllSuppliers().stream()
                        .map(supplierDto -> new SupplierTm(
                                supplierDto.getSupId(),
                                supplierDto.getSupName(),
                                supplierDto.getSupContact()
                        )).toList()
        ));
    }

    public void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText("");
            txtContact.setText("");
            tblSupplier.getItems().clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String supplierId = txtName.getText();
        String supplierName = txtContact.getText();
        String supplierContact = txtContact.getText();

        boolean isValidName = supplierName.matches(namePattern);
        boolean isValidContact = supplierContact.matches(contactPattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidName) txtName.setStyle(";-fx-border-color: red;");
        if (!isValidContact) txtContact.setStyle(";-fx-border-color: red;");

        SupplierDto supplierDto = new SupplierDto(
                supplierId,
                supplierName,
                supplierContact
        );

        if (isValidName && isValidContact) {
            try {
                boolean isSaved = supplierModel.saveSupplier(supplierDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Saved").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
            }
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String supplierId = txtName.getText();
        String supplierName = txtContact.getText();
        String supplierContact = txtContact.getText();

        SupplierDto supplierDto = new SupplierDto(
                supplierId,
                supplierName,
                supplierContact
        );

        try {
            boolean isUpdated = supplierModel.updateSupplier(supplierDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Successfully Updated").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String supplierId = txtName.getText();
            try {
                boolean isDeleted = supplierModel.deleteSupplier(supplierId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
                }
            }
        }
        private void loadNextId()throws SQLException, ClassNotFoundException {
            String nextId = supplierModel.getNextSupplierId();
            lblSupplierId.setText(nextId);
        }

    public void onClickTable(MouseEvent mouseEvent) {
        SupplierTm selectedItem = tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSupplierId.setText(selectedItem.getSupplierId());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
