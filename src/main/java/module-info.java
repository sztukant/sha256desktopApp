module com.example.sha256desktopapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.sha256desktopapp to javafx.fxml;
    exports com.example.sha256desktopapp;
}