package lk.ijse.alphamodifications.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;

public class LoginPageContraller {
    public Button btnLogin;
    public TextField txtPassword;
    public TextField txtUsername;
    public Button btnSignUp;

    private final String userNamePattern = "^[A-Za-z0-9_ ]{3,}$";
    private final String passwordPattern = "^[A-Za-z0-9@#$%^&+=]{6,}$";

    public void initialize(){
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());

        btnLogin.setDisable(true);
    }

    private void validateFields() {
        boolean isValidUsername = txtUsername.getText().matches(userNamePattern);
        boolean isValidPassword = txtPassword.getText().matches(passwordPattern);

        txtUsername.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        if(!isValidUsername) txtUsername.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidPassword) txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        btnLogin.setDisable(!(isValidUsername && isValidPassword));
    }


    public void btnLoginOnAction(ActionEvent actionEvent) {
        String inputUsername = txtUsername.getText();
        String inputPassword = txtPassword.getText();

        if(inputUsername.isEmpty() || inputPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter username and password", ButtonType.OK).show();
            return;
        }
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE user_name = ? AND password = ?",
                    inputUsername,inputPassword
                    );
            if (resultSet.next()){
                try {
                    Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/view/DashboardPage.fxml"));
                    Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    dashBoardStage.setScene(new Scene(dashboardRoot));
                    dashBoardStage.setTitle("Alpha Modifications");
                    dashBoardStage.setResizable(true);
                    dashBoardStage.show();
                }catch (Exception e){
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid UserName or Password", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Login Failed", ButtonType.OK).show();
        }
    }


    public void btnSignUpOnAction(ActionEvent actionEvent) {
        try {
            Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
            Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            dashBoardStage.setScene(new Scene(dashBoardRoot));
            dashBoardStage.setTitle("Alpha Modifications");
            dashBoardStage.setResizable(true);
            dashBoardStage.show();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SignUp Failed", ButtonType.OK).show();
        }
    }
}



