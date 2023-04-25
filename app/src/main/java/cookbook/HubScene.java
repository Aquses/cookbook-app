package cookbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HubScene implements Initializable {
    @FXML
    private Button MenuClosed;
    @FXML
    private Button MenuOpen;
    @FXML
    private HBox MenuSlider;
    @FXML
    private Button RecipesButton;
    @FXML
    private AnchorPane ap;
    @FXML
    private Pane darkenPane;
    @FXML
    private Button HomeButton;

    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("HubScene.fxml"));
        Scene hub = new Scene(fxmlLoader.load(), 1280, 720);

        return hub;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commonMenuControls();
    }

    private void commonMenuControls() {
        MenuSlider.setPrefWidth(50);
        darkenPane.setVisible(false);

        MenuClosed.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(143);

            MenuOpen.setVisible(true);
            MenuClosed.setVisible(false);
            darkenPane.setVisible(true);
        });

        MenuOpen.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
        });

        darkenPane.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
        });

        HomeButton.setOnMouseClicked(event -> {
            MenuSlider.setPrefWidth(50);

            MenuOpen.setVisible(false);
            MenuClosed.setVisible(true);
            darkenPane.setVisible(false);
        });

        RecipesButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ap.getScene().getWindow();
            try {
                stage.setScene(RecipesScene.getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        HomeButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) ap.getScene().getWindow();
            try {
                stage.setScene(HubScene.getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
