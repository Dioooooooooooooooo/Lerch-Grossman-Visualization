package com.example.minerz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends Application {

    public static final String URL = "jdbc:mysql://localhost:3306/dbminerz";
    public static final String USER = "root";
    public static final String PASS = "";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Register.class.getResource("register.fxml"));
        Parent root = fxmlLoader.load();

        stage.getIcons().add(new Image(Register.class.getResourceAsStream("/logo.png")));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try (Connection c = Register.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS tbluseraccount(" +
                             "id INT AUTO_INCREMENT PRIMARY KEY," +
                             "username VARCHAR(50) NOT NULL," +
                             "password VARCHAR(100) NOT NULL)"
             )) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch();
    }


    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

}
