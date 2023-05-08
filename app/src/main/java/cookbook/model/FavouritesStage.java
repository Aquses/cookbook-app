package cookbook.controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FavouritesStage {
    public static void favouritesStage(int user_id) throws SQLException {

        Stage stage = new Stage();
        stage.setTitle("Favourites");
        stage.setMinWidth(600);
        stage.setMinHeight(200);

        ListView<String> favoutesList = new ListView<>();
        Color color1 = Color.LIGHTBLUE;
        Color color2 = Color.LIGHTGREY;
        favoutesList.setCellFactory(param -> new ListCell<>() {
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

        // get favourites
        DataQuery db = new DataQuery();
        Map<String, String> recipes = db.getAllFavouriteRecipes(user_id);
        List<String> result = recipes.keySet().stream().collect(Collectors.toList());

        // Add a listener to the ListView to show the popup when an item is clicked
        favoutesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Get the value for the selected key
            String value = recipes.get(newValue);

            // Create a popup and set its content to the value
            Popup popup = new Popup();
            popup.setAutoHide(false); // Disable auto-hide
            popup.setX(favoutesList.getScene().getWindow().getX() + favoutesList.getWidth() + 10); // Position popup next to ListView
            popup.setY(favoutesList.getScene().getWindow().getY() + favoutesList.getLayoutY() + favoutesList.getSelectionModel().getSelectedIndex() * favoutesList.getFixedCellSize()); // Position popup next to ListView item

            // Create a close button
            Button closeButton = new Button("Close");
            closeButton.setOnAction(event -> popup.hide()); // Hide popup when close button is clicked

            // Create a VBox to hold the content and close button
            VBox vBox = new VBox();
            vBox.setStyle("-fx-background-color: white; -fx-padding: 10px;");
            vBox.getChildren().addAll(new Label(value), closeButton);

            // Set the content of the popup to the VBox
            popup.getContent().add(vBox);

            // Make the popup draggable
            vBox.setOnMousePressed(event -> {
                vBox.setCursor(Cursor.CLOSED_HAND);
                vBox.setMouseTransparent(true);
                popup.setX(event.getScreenX() - popup.getWidth() / 2);
                popup.setY(event.getScreenY() - popup.getHeight() / 2);
            });
            vBox.setOnMouseDragged(event -> {
                popup.setX(event.getScreenX() - popup.getWidth() / 2);
                popup.setY(event.getScreenY() - popup.getHeight() / 2);
            });
            vBox.setOnMouseReleased(event -> {
                vBox.setCursor(Cursor.DEFAULT);
                vBox.setMouseTransparent(false);
            });

            // Show the popup
            popup.show(favoutesList.getScene().getWindow());
        });

        favoutesList.getItems().clear();
        favoutesList.getItems().addAll(result.size() == 0 ? Arrays.asList("No Result Found!!") : result);

        // Create layout
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(favoutesList);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

