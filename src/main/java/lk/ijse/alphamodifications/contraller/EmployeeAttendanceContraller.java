package lk.ijse.alphamodifications.contraller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.alphamodifications.dto.EmpAttendanceDto;
import lk.ijse.alphamodifications.dto.tm.EmpAttendanceTm;
import lk.ijse.alphamodifications.model.EmpAttendanceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeAttendanceContraller implements Initializable {

    public Label lblAttendanceId;
    public TextField txtEmpId;
    public TextField txtDate;
    public TextField txtAttendTime;
    public TextField txtDuration;


    public TableView<EmpAttendanceTm> tblAttendance;
    public TableColumn<EmpAttendanceTm, String> colAttId;
    public TableColumn<EmpAttendanceTm, String > colEmpId;
    public TableColumn<EmpAttendanceTm, String > colDate;
    public TableColumn<EmpAttendanceTm, String > colAttendTime;
    public TableColumn<EmpAttendanceTm, String > colDuration;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        colAttId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAttendTime.setCellValueFactory(new PropertyValueFactory<>("attendTime"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
        }
    }

    private final EmpAttendanceModel empAttendanceModel = new EmpAttendanceModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;

    private final String employeeIdPattern = "^E\\d{3}$";
    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String timePattern = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    private final String durationPattern = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";




    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblAttendance.setItems(FXCollections.observableArrayList(
                empAttendanceModel.getAllAttendance().stream()
                        .map(empAttendanceDto -> new EmpAttendanceTm(
                                empAttendanceDto.getAttId(),
                                empAttendanceDto.getEmpId(),
                                empAttendanceDto.getDate(),
                                empAttendanceDto.getAttendTime(),
                                empAttendanceDto.getDuration()
                        )).toList()
        ));
    }
    public void resetPage(){
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtEmpId.setText("");
            txtDate.setText("");
            txtAttendTime.setText("");
            txtDuration.setText("");
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String attId = txtEmpId.getText();
        String empId = txtDate.getText();
        String date = txtAttendTime.getText();
        String attTime = txtAttendTime.getText();
        String duration = txtDuration.getText();

        boolean isValidEmpId = empId.matches(employeeIdPattern);
        boolean isValidDate = date.matches(datePattern);
        boolean isValidAttTime = attTime.matches(timePattern);
        boolean isValidDuration = duration.matches(durationPattern);

        txtEmpId.setStyle(txtEmpId.getStyle() + ";-fx-border-color: #7367F0;");
        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0");
        txtAttendTime.setStyle(txtAttendTime.getStyle() + ";-fx-border-color: #7367F0");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidEmpId) txtEmpId.setStyle(txtEmpId.getStyle() + ";-fx-border-color: red;");
        if (!isValidDate) txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: red;");
        if (!isValidAttTime) txtAttendTime.setStyle(txtAttendTime.getStyle() + ";-fx-border-color: red;");
        if (!isValidDuration) txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red");

        EmpAttendanceDto empAttendanceDto = new EmpAttendanceDto(
                attId,
                empId,
                date,
                attTime,
                duration
        );

        if (isValidEmpId && isValidDate && isValidAttTime && isValidDuration){
            try {
                boolean isSaved = empAttendanceModel.saveAttendance(empAttendanceDto);

                if (isSaved){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Attendance Saved").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String attId = txtEmpId.getText();
        String empId = txtDate.getText();
        String date = txtAttendTime.getText();
        String attTime = txtAttendTime.getText();
        String duration = txtDuration.getText();

        EmpAttendanceDto empAttendanceDto = new EmpAttendanceDto(
                attId,
                empId,
                date,
                attTime,
                duration
        );
        try {
            boolean isUpdated = empAttendanceModel.saveAttendance(empAttendanceDto);

            if (isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Attendance Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                    ButtonType.YES,
                    ButtonType.NO
                );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES){
            String attId = txtEmpId.getText();

            try {
                boolean isDeleted = empAttendanceModel.deleteAttendance(attId);
                if (isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Attendance Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
            }
        }
    }

    private void loadNextId()throws ClassNotFoundException ,SQLException{
        EmpAttendanceTm selectedItem = tblAttendance.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblAttendanceId.setText(selectedItem.getAttendanceId());
            txtEmpId.setText(selectedItem.getEmpId());
            txtDate.setText(selectedItem.getDate());
            txtAttendTime.setText(selectedItem.getAttendTime());
            txtDuration.setText(selectedItem.getDuration());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}

