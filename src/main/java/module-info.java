module com.example.minerz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.minerz to javafx.fxml;
    exports com.example.minerz;
}