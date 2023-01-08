module com.example.virtualmusicpad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.virtualmusicpad to javafx.fxml;
    exports com.example.virtualmusicpad;
}