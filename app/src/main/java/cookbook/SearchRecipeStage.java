package cookbook;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SearchRecipeStage {

    public static void searchRecipeStage() {
        Stage stage = new Stage();
        stage.setTitle("Search Recipe");
        stage.setMinWidth(600);
        stage.setMinHeight(300);

        // Create UI controls
        Label searchLabel = new Label("Search for Recipe:");
        TextField searchField = new TextField();

        Button searchButton = new Button("Search");

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

        // Create layout
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(searchLabel, searchField, searchButton, searchResultList);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Set up button action
        searchButton.setOnAction(event -> {
            // Perform search and update resultBox accordingly
            String query = searchField.getText();
            DataQuery dataQuery = new DataQuery();
            try {
                List<String> result = dataQuery.searchByName(query);
                searchResultList.getItems().clear();
                searchResultList.getItems().addAll(result.size() == 0 ? Arrays.asList("No Result Found!!") : result);
            } catch (SQLException e) {

            }
        });
    }
}
