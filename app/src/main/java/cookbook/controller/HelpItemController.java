package cookbook.controller;

import cookbook.Cookbook;
import cookbook.model.Help;
import cookbook.view.DisplayTutorialScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpItemController implements Initializable {
    @FXML
    private Button MoreButton;
    private AnchorPane parentAnchorPane;
    private Help help;

    public void setData(Help help, AnchorPane parent) {
        this.help = help;
        parentAnchorPane = parent;
        MoreButton.setText(help.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MoreButton.setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("DisplayTutorialScene.fxml"));
            DisplayTutorialScene dts;
            Node n;

            // load first
            try {
                n = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // then get controller
            dts = fxmlLoader.getController();
            dts.addRecipeObject(help, parentAnchorPane);

            AnchorPane.setTopAnchor(n, 0.0);
            AnchorPane.setRightAnchor(n, 0.0);
            AnchorPane.setBottomAnchor(n, 0.0);
            AnchorPane.setLeftAnchor(n, 0.0);

            parentAnchorPane.getChildren().clear();
            parentAnchorPane.getChildren().add(n);
        });
    }
}
