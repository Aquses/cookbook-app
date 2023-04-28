package cookbook.controller;

import cookbook.Cookbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HubScene implements Initializable {

    @FXML
    private AnchorPane ap;

    public static Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("HubScene.fxml"));
        Scene hub = new Scene(fxmlLoader.load(), 1280, 700);

        return hub;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
