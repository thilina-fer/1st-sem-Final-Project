package lk.ijse.alphamodifications.contraller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.alphamodifications.dto.EmployeeDto;
import lk.ijse.alphamodifications.dto.tm.EmployeeTm;
import lk.ijse.alphamodifications.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePageContraller implements Initializable {
    public Label lblEmployeeId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtSalary;


    public TableView<EmployeeTm> tblEmployee;
    public TableColumn<EmployeeTm , String> colId;
    public TableColumn<EmployeeTm , String> colName;
    public TableColumn<EmployeeTm , String> colContact;
    public TableColumn<EmployeeTm , String> colAddress;
    public TableColumn<EmployeeTm , Integer> colAge;
    public TableColumn<EmployeeTm , Double> colSalary;


    private final EmployeeModel employeeModel = new EmployeeModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String userNamePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^[0-9]{10}$";
    private final String addressPattern = "^[A-Za-z ]+$";
    private final String agePattern = "^(\\d+)$";
    private final String salaryPattern = "^(\\d+)$";

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        colId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("employeeAge"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong, please try again").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(FXCollections.observableArrayList(
                employeeModel.getAllEmployee().stream()
                        .map(employeeDto -> new EmployeeTm(
                                employeeDto.getEmpId(),
                                employeeDto.getEmpName(),
                                employeeDto.getEmpContact(),
                                employeeDto.getEmpAddress(),
                                employeeDto.getEmpAge(),
                                employeeDto.getSalary()
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
            txtContact.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            txtSalary.setText("");
        }catch (Exception e){
            e.printStackTrace();
            new  Alert(Alert.AlertType.ERROR,"Something went wrong, please try again").show();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String empId = lblEmployeeId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String age = txtAge.getText();
        String salary = txtSalary.getText();

        boolean isValidName = name.matches(userNamePattern);
        boolean isValidContact = contact.matches(contactPattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidAge = age.matches(agePattern);
        boolean isValidSalary = salary.matches(salaryPattern);

        txtName.setStyle(txtName.getStyle()+";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: #7367F0");
        txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: #7367F0");
        txtAge.setStyle(txtAge.getStyle()+";-fx-border-color: #7367F0");
        txtSalary.setStyle(txtSalary.getStyle()+";-fx-border-color: #7367F0");

        if (!isValidName) txtName.setStyle(txtName.getStyle()+";-fx-border-color: red;");
        if (!isValidContact) txtContact.setStyle(txtContact.getStyle()+";-fx-border-color: red;");
        if (!isValidAddress) txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: red;");
        if (!isValidAge) txtAge.setStyle(txtAge.getStyle()+";-fx-border-color: red;");
        if (!isValidSalary) txtSalary.setStyle(txtSalary.getStyle()+";-fx-border-color: red;");

        int parsedAge = Integer.parseInt(age);
        double parsedSalary = Double.parseDouble(salary);

        EmployeeDto employeeDto = new EmployeeDto(
                empId,
                name,
                contact,
                address,
                parsedAge,
                parsedSalary
        );
        if (isValidName && isValidContact && isValidAddress && isValidAge && isValidSalary) {
            try {
                boolean isSaved = employeeModel.saveEmployee(employeeDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Saved").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Save failed").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Save failed").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String empId = lblEmployeeId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String age = txtAge.getText();
        String salary = txtSalary.getText();

        int parsedAge = Integer.parseInt(age);
        double parsedSalary = Double.parseDouble(salary);

        EmployeeDto employeeDto = new EmployeeDto(
                empId,
                name,
                contact,
                address,
                parsedAge,
                parsedSalary
        );
            try {
                boolean isUpdated = employeeModel.updateEmployee(employeeDto);

                if (isUpdated) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Updated").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Update failed").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Update failed").show();
            }
        }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you Sure ?",
                ButtonType.YES,
                ButtonType.NO
                );

        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String empId = lblEmployeeId.getText();
            try {
                boolean isDeleted = employeeModel.deleteEmployee(empId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Delete failed").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Delete failed").show();
            }
        }
    }
private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextId);
}

    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblEmployeeId.setText(selectedItem.getEmployeeId());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());
            txtAddress.setText(selectedItem.getAddress());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
