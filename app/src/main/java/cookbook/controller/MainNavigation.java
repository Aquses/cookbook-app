package cookbook.controller;

import cookbook.Cookbook;
import cookbook.model.Session;
import cookbook.model.User;
import cookbook.view.RecipesScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainNavigation implements Initializable {
    @FXML
    private Button MenuClosed;
    @FXML
    private Button MenuOpen;
    @FXML
    private HBox MenuSlider;
    @FXML
    private Button RecipesButton;
    @FXML
    private Button AdminButton;
    @FXML
    private Pane darkenPane;
    @FXML
    private Button HomeButton;

    @FXML
    private Button MessagesButton;

    @FXML
    private AnchorPane ContentAnchor;
 
    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("NavBar.fxml"));
        Scene hub = new Scene(fxmlLoader.load(), 1280, 700);

        return hub;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
				User currentUser = Session.getCurrentUser();
        AdminButton.setVisible(currentUser.getIsAdmin());
        try {
            loadScene(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
			menuControls();
    }

    private void menuControls() {
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
            try {
                loadScene(1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        HomeButton.setOnMouseClicked(event -> {
            try {
                loadScene(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        
        AdminButton.setOnMouseClicked(event -> {
            try {
                loadScene(2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        MessagesButton.setOnMouseClicked(event -> {
            try {
                loadScene(3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void loadScene(int sceneID) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Node n;

        switch (sceneID){
            // Load hub
            case 0:
                fxmlLoader.setLocation(Cookbook.class.getResource("HubScene.fxml"));
                break;
            // Load recipes scene
            case 1:
                // set logged in user on recipe scene
                fxmlLoader.setLocation(Cookbook.class.getResource("RecipesScene.fxml"));
                break;
            // Load admin scene
            case 2:
                fxmlLoader.setLocation(Cookbook.class.getResource("AdminScene.fxml"));
                break;
            // Load hub when given an invalid number
            case 3:
                fxmlLoader.setLocation(Cookbook.class.getResource("MessagesScene.fxml"));
                break;
            default:
                IOException wrongSceneIDException = new IOException("The provided scene ID to load does not exist.");
                throw wrongSceneIDException;
        }

        n = fxmlLoader.load();
        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);

        ContentAnchor.getChildren().clear();
        ContentAnchor.getChildren().add(n);

        return;
    }
}
