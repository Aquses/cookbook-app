package cookbook;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HubController implements Initializable {

    @FXML
    private Button search_bar;

    @FXML
    private Button browse_bar;

    @FXML
    private Label text_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        search_bar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ChangeScenes.changeScene(event, "searchpage.fxml");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        browse_bar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ChangeScenes.changeScene(event, "browserecipe.fxml");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
