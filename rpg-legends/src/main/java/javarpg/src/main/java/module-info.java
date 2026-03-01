module com.henry {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.henry to javafx.fxml;
    exports com.henry;
}

