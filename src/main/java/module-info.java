module com.example.minerz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minerz to javafx.fxml;
    exports com.example.minerz;
}