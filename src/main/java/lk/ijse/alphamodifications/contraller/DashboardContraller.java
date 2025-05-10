package lk.ijse.alphamodifications.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardContraller implements Initializable {

    public AnchorPane ancDashboard;

    public void btnGoToItemOnAction(ActionEvent actionEvent) {
        navigateTo("/view/ItemPage.fxml");
    }

    public void btnGoToEmployeeOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmployeePage.fxml");
    }

    public void btnGoToAttendanceOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmployeeAttendancePage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateTo("/view/ItemPage.fxml");

    }

    private void navigateTo(String path) {
        try {
            ancDashboard.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashboard.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashboard.heightProperty());

            ancDashboard.getChildren().add(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "2 went wrong").show();
            e.printStackTrace();
        }
    }

    public void btnOnActionCustomer(ActionEvent actionEvent) {
        navigateTo("/view/CustomerPage.fxml");
    }
}
