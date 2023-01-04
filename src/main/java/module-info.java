module com.example.virtualmusicpad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.virtualmusicpad to javafx.fxml;
    exports com.example.virtualmusicpad;
}