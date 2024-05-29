package com.example.minerz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private int userId;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField loginPassInput;

    @FXML
    private TextField loginUserInput;

    @FXML
    private Label noAccount;

    @FXML
    private Hyperlink backToRegister;

    @FXML
    private Hyperlink resetPassword;

    @FXML
    private void initialize() {
        btnLogin.setOnAction(this::login);
        backToRegister.setOnAction(this::backRegister);
        resetPassword.setOnAction(this::setResetPassword);
    }

    @FXML
    private void setResetPassword(ActionEvent event){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Login.class.getResourceAsStream("/grass.png")));
            stage.setScene(new Scene(root));
            stage.setTitle("Reset Password");
            stage.show();

            ((Stage) resetPassword.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void backRegister(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Login.class.getResourceAsStream("/grass.png")));
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();

            ((Stage) backToRegister.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void login(ActionEvent event) {
        String username = loginUserInput.getText();
        String password = loginPassInput.getText();
        boolean authenticated = authenticate(username, password);
        if (authenticated) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("land.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(Login.class.getResourceAsStream("/grass.png")));
                stage.setScene(new Scene(root));
                stage.setTitle("Watchlist");
                stage.setTitle("Lerch-Grossman");
                stage.setMaximized(true);
                stage.show();

                Stage loginStage = (Stage) btnLogin.getScene().getWindow();
                loginStage.close();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        } else if(loginPassInput.getText().isEmpty() || loginPassInput.getText().isEmpty()){
            noAccount.setText("Details Empty");
        }
        else {
            noAccount.setText("Wrong username or password");
        }
    }



    private boolean authenticate(String username, String password) {
        try (Connection c = Register.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT * FROM tbluseraccount WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");
                String hashedPassword = PasswordUtils.hashPassword(password);
                if (hashedPassword != null && hashedPassword.equals(storedHashedPassword)) {
                    userId = resultSet.getInt("id");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
