package cookbook;

import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;


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
                StackPane stageLayout = new StackPane();   

                try {
                    DataQuery formattedQuery = new DataQuery();
                    stageLayout.getChildren().add(formattedQuery.getFormattedRecipe(recipeName));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                // set recipe scene and show stage
                Scene recipeStageScene = new Scene(stageLayout);
                recipeStage.setScene(recipeStageScene);
                recipeStage.setTitle("Dish IT");
                recipeStage.show(); 
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

        
        
        /*try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Cookbook?user=root&password=!!@@qqww3344EERR&useSSL=false");
                 
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT recipe_name FROM recipes");
            while (rs.next()) {
                Label data = new Label(rs.getString(1));
                String dataText = data.getText();
                data.setOnMouseClicked (e -> {
                    Stage recipeStage = new Stage();
                    StackPane stageLayout = new StackPane();

                    try {
                        Statement getRecipe = conn.createStatement();
                        ResultSet recipe = getRecipe.executeQuery("SELECT * FROM recipes WHERE recipe_name = \"" + dataText +"\"");

                        while (recipe.next()) {
                        Label attributes = new Label(recipe.getString(2) + "\n\n" + recipe.getString(3) + "\n" + recipe.getString(4)
                            + "\n" + "Servings: " + recipe.getString(5) + "\n" + "Prep Time: " + recipe.getString(6) + " Minutes " + "\n"
                            + " Cook Time: " + recipe.getString(7) + " Minutes");
                        stageLayout.getChildren().add(attributes);
                        }                     
                    } catch (SQLException e2) {
                        System.out.println("An error has occurred");
                    }     
                Scene recipeStageScene = new Scene(stageLayout);
                recipeStage.setScene(recipeStageScene);
                recipeStage.setTitle("Dish IT");
                recipeStage.show(); 
                });
            root.getChildren().add(data);
            }
        } catch (SQLException e) {
                System.out.println("An error has occurred");
        }
        Scene scene = new Scene(root, 700, 600);
        return scene;
        }
}*/


