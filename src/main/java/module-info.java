module com.example.virtualmusicpad {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.xml;


    opens com.example.virtualmusicpad to javafx.fxml;
    exports com.example.virtualmusicpad;
}