package cookbook.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import cookbook.Cookbook;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPageScene {
      
    public static Scene getUserPage() {
        //recipe list 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        Label title = new Label("Recipe List");
        title.setStyle("-fx-font-weight: bold;");
        title.setUnderline(true);
        root.getChildren().add(title);

        //search recipe button
        Button searchButton = new Button("Search Recipe");
        searchButton.setOnAction(e -> {
            try {
                /*
                // Load the FXML file for the search page
                Parent searchPageParent = FXMLLoader.load(UserPageScene.class.getResource("searchpage.fxml"));
                // Create a new scene with the loaded FXML file
                Scene searchScene = new Scene(searchPageParent);

                 */
                // Get the current stage and set the scene to the search scene
                FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("searchpage.fxml"));
                Scene searchScene = new Scene(fxmlLoader.load(), 1280, 720);

                Stage stage = (Stage) searchButton.getScene().getWindow();
                stage.setScene(searchScene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        root.getChildren().add(searchButton);

        //add recipe button
        Button button2 = new Button();

        button2.setText("Add Recipe");
        button2.setLayoutX(50);
        button2.setLayoutY(50);
        button2.setOnAction(e2 -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AddRecipeController.class.getResource("/cookbook/AddRecipeScene.fxml"));                
                Parent addRecipeRoot = fxmlLoader.load();
                Scene addRecipeScene = new Scene(addRecipeRoot);
                Stage currentStage = (Stage) button2.getScene().getWindow();
                currentStage.setScene(addRecipeScene);
            } catch (IOException e) {
              e.printStackTrace();
            }
        });

        root.getChildren().add(button2);

        //add list of all recipes
        DataQuery dataQuery = new DataQuery();
        List<String> allRecipes;
        try {

            allRecipes = dataQuery.getAllRecipes();


        for (String recipeName : allRecipes) {
            Label recipe = new Label(recipeName);
            recipe.setOnMouseClicked(e -> {
                Stage recipeStage = new Stage();
                DataQuery formattedQuery = new DataQuery();   

                try {
                    Scene recipeStageScene = new Scene(formattedQuery.getFormattedRecipe(recipeName));
                    recipeStage.setScene(recipeStageScene);
                    recipeStage.setTitle("Dish IT");
                    recipeStage.show(); 
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            root.getChildren().add(recipe);
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        Scene scene = new Scene(root, 700, 500, false, null);
        return scene;
    }
}

