module com.cookbook.cookbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.cookbook.cookbook to javafx.fxml;
    exports com.cookbook.cookbook;
}