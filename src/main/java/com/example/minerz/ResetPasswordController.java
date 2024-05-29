package com.example.minerz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private Hyperlink backtoLogin;

    @FXML
    private PasswordField resetPass;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private TextField usernameCheck;

    @FXML
    private Button sumbitChange;

    @FXML
    private Text resetStatus;

    @FXML
    private void changePass(ActionEvent event) {
        String username = usernameCheck.getText();
        String newPassword = resetPass.getText();
        String confirmPassword = confirmPass.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            resetStatus.setText("Please fill in all fields.");
            MediaUtil.getInstance().playSoundEffect();

        } else if (!newPassword.equals(confirmPassword)) {
            resetStatus.setText("Passwords do not match.");
            MediaUtil.getInstance().playSoundEffect();

        } else {
            boolean resetSuccessful = resetPassword(username, newPassword);
            if (resetSuccessful) {
                resetStatus.setText("Password changed successfully.");
                MediaUtil.getInstance().playSoundEffect();

            } else {
                resetStatus.setText("Failed to change password. Please try again.");
                MediaUtil.getInstance().playSoundEffect();

            }
        }
    }

    private boolean resetPassword(String username, String newPassword) {

        String hashedPassword = PasswordUtils.hashPassword(newPassword);
        if (hashedPassword == null) {
            return false;
        }

        try (Connection c = Register.getConnection()) {
            try (PreparedStatement checkStatement = c.prepareStatement("SELECT COUNT(*) FROM tbluseraccount WHERE username = ?")) {
                checkStatement.setString(1, username);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    if (count == 0) {
                        return false;
                    }
                }
            }

            try (PreparedStatement statement = c.prepareStatement("UPDATE tbluseraccount SET password = ? WHERE username = ?")) {
                statement.setString(1, hashedPassword);
                statement.setString(2, username);
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void setBacktoLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Login.class.getResourceAsStream("/grass.png")));
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();

            ((Stage) backtoLogin.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
