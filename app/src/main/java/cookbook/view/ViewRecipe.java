package cookbook.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewRecipe {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label tempHeader;

    @FXML
    void initialize() {
        assert tempHeader != null : "fx:id=\"tempHeader\" was not injected: check your FXML file 'viewrecipe.fxml'.";

    }
}
