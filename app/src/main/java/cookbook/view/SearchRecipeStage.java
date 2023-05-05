package cookbook.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import cookbook.model.DataQuery;

public class SearchRecipeStage {

    public static void searchRecipeStage() {
        Stage stage = new Stage();
        stage.setTitle("Search Recipe ");
        stage.setMinWidth(600);
        stage.setMinHeight(300);

        // Create UI controls
        Label searchLabel = new Label("Search for Recipe By Name:");
        TextField searchField = new TextField();

        Label searchLabe2 = new Label("Search for Recipe By ingredient:");
        TextField searchField2 = new TextField();

        Button searchButton1 = new Button("Find recipes by name");

        Button searchButton2 = new Button("Find recipes by ingredient");

        ListView<String> searchResultList = new ListView<>();
        Color color1 = Color.LIGHTBLUE;
        Color color2 = Color.LIGHTGREY;
        searchResultList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    // Alternate the colors based on the index of the item
                    if (getIndex() % 2 == 0) {
                        setBackground(new Background(new BackgroundFill(color1, null, null)));
                    } else {
                        setBackground(new Background(new BackgroundFill(color2, null, null)));
                    }
                }
            }
        });


        ListView<String> searchResultListForIngred = new ListView<>();
        Color INcolor1 = Color.BLUE;
        Color INcolor2 = Color.LIGHTGREY;
        searchResultListForIngred.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    // Alternate the colors based on the index of the item
                    if (getIndex() % 2 == 0) {
                        setBackground(new Background(new BackgroundFill(INcolor1, null, null)));
                    } else {
                        setBackground(new Background(new BackgroundFill(INcolor2, null, null)));
                    }
                }
            }
        });

        // Create layout
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(searchLabel, searchField, searchButton1, searchResultList, searchLabe2, searchField2, searchButton2, searchResultListForIngred);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Set up button action
        searchButton1.setOnAction(event -> {
            // Perform search and update resultBox accordingly
            String query = searchField.getText();
            DataQuery dataQuery = new DataQuery();
            try {
                List<String> result = dataQuery.searchByName(query);
                searchResultList.getItems().clear();
                searchResultList.getItems().addAll(result.size() == 0 ? Arrays.asList("Sorry, there is no recipe for this ingredient!") : result);
            } catch (SQLException e) {

            }
        });

        searchButton1.setOnAction(event -> {
            // Perform search and update resultBox accordingly
            String query = searchField2.getText();
            DataQuery dataQuery = new DataQuery();
            try {
                List<String> result = dataQuery.searchByIngredient(query);
                searchResultListForIngred.getItems().clear();
                searchResultListForIngred.getItems().addAll(result.size() == 0 ? Arrays.asList("Sorry, there is no recipe for this ingredient!") : result);
            } catch (SQLException e) {

            }
        });
    }
}
