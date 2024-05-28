module com.example.minerz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.minerz to javafx.fxml;
    exports com.example.minerz;
}