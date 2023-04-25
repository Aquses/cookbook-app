package cookbook;

import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPageScene {
      
    public Scene getUserPage() {
        //recipe list 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        Label title = new Label("Recipe List");
        title.setStyle("-fx-font-weight: bold;");
        title.setUnderline(true);
        root.getChildren().add(title);

        //add recipe button
        Button button = new Button();

        button.setText("Add Recipe");
        button.setLayoutX(50);
        button.setLayoutY(50);
        button.setOnAction(e2 -> {
        AddRecipeStage.addRecipeStage();
        });        
        root.getChildren().add(button);

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
        Scene scene = new Scene(root, 700, 700, false, null);
        return scene;
    }
}

