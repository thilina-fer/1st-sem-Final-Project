package lk.ijse.alphamodifications.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.alphamodifications.dto.tm.EmployeeTm;

public class EmployeePageContraller implements Initializable {
    public Label lblEmployeeId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtSalary;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TableView<EmployeeTm> tblEmployee;
    public TableColumn<EmployeeTm , String> colId;
    public TableColumn<EmployeeTm , String> colName;
    public TableColumn<EmployeeTm , String> colContact;
    public TableColumn<EmployeeTm , String > colAddress;
    public TableColumn<EmployeeTm , ?> colAge;
    public TableColumn<EmployeeTm , ?> colSalary;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPatern = "^[A-Za-z ]+$";
    private final String agePatern = "^(\\d+)$";
    private final String salaryPatern = "^(\\d+)$";

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
