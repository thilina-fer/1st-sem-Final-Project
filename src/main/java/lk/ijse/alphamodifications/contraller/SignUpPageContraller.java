package lk.ijse.alphamodifications.contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.alphamodifications.dto.UserDto;
import lk.ijse.alphamodifications.model.UserModel;
import lk.ijse.alphamodifications.util.CrudUtil;

import java.sql.ResultSet;

public class SignUpPageContraller {
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtPassword;
    public TextField txtRePassword;
    public Button btnSignUp;


    private final String namePattern = "^[A-Za-z ]+$";
    private final String addressPattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final String phonePattern = "\\d{10}";
    private final String userNamePattern = "^[A-Za-z0-9_]{3,}$";
    private final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";

    public void initialize(){
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtAddress.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtContact.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtRePassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());

        btnSignUp.setDisable(true);

    }

    private void validateFields(){
        boolean isValidUsername = txtUsername.getText().matches(userNamePattern);
        boolean isValidEmail = txtEmail.getText().matches(emailPattern);
        boolean isValidAddress = txtAddress.getText().matches(addressPattern);
        boolean isValidContact = txtContact.getText().matches(phonePattern);
        boolean isValidPassword = txtPassword.getText().matches(passwordPattern);
        boolean isValidRePassword = txtRePassword.getText().equals(txtRePassword.getText());

        txtUsername.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtEmail.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtAddress.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtContact.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtRePassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        if (!isValidUsername) txtUsername.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidEmail) txtEmail.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidAddress) txtAddress.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidContact) txtContact.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if(!isValidPassword) txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidRePassword) txtRePassword.setText("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        btnSignUp.setDisable(!isValidUsername && !isValidEmail && !isValidAddress && !isValidContact && !isValidPassword && !isValidRePassword);

    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        String inputUserName = txtUsername.getText();
        String inputEmail = txtEmail.getText();
        String inputAddress = txtAddress.getText();
        String inputContact = txtContact.getText();
        String inputPassword = txtPassword.getText();
        String inputRePassword = txtRePassword.getText();

        if (inputUserName.isEmpty() || inputEmail.isEmpty() || inputAddress.isEmpty() || inputContact.isEmpty() || inputPassword.isEmpty() || inputRePassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fil all Fields").show();
            return;
        }
        if (!inputEmail.matches(emailPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid emil format");
            return;
        }
        if (!inputAddress.matches(addressPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid address format");
            return;
        }
        if (!inputContact.matches(phonePattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact format");
            return;
        }
        if (!inputPassword.equals(inputRePassword)) {
            new Alert(Alert.AlertType.ERROR, "Wrong password");
            return;
        }
        if (!inputPassword.equals(inputRePassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match");
            return;
        }
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE user_name = ? OR emil = ? ", inputUserName , inputEmail);
            if (resultSet.next()){
                new Alert(Alert.AlertType.ERROR, "User already exists").show();
                return;
            }
            String userId = UserModel.getNextUserId();

            boolean isSaved = UserModel.saveUser(new UserDto(userId , inputUserName , inputEmail , inputAddress , inputContact , inputPassword , inputRePassword ));

            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "User has been saved successfully").show();
                btnSignUpOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.ERROR, "User could not be saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Sign Up Faild").show();
        }
    }

    public void btnBackLoginOnAction(ActionEvent actionEvent) {
        try {
            Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            dashBoardStage.setScene(new Scene(dashBoardRoot));
            dashBoardStage.setTitle("Alpha");
            dashBoardStage.setResizable(true);
            dashBoardStage.show();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load to Login page").show();
        }
    }
}
