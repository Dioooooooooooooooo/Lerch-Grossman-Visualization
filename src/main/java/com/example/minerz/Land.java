package com.example.minerz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
public class Land extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Land.class.getResource("land.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.getIcons().add(new Image(Login.class.getResourceAsStream("/grass.png")));

        URL cursorImageUrl = getClass().getResource("/cursor.png");
        Image cursorImage = new Image(cursorImageUrl.toString());
        scene.setCursor(new ImageCursor(cursorImage));

        stage.setTitle("Miners");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}