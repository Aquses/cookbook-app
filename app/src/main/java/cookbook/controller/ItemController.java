package cookbook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ItemController {
    @FXML
    private ImageView FoodImage;
    @FXML
    private Label FoodItemName;
    @FXML
    private Label ServingsCount;
    @FXML
    private Label TimeTakenCount;
    private Recipe recipe;

    public void setData(Recipe recipe){
        this.recipe = recipe;

        System.out.println(recipe.getName());
        this.FoodItemName.setText(recipe.getName());
        this.ServingsCount.setText(Float.toString(recipe.getServings()));
        this.TimeTakenCount.setText(Float.toString((recipe.getCookTime())));
        //Image image = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
        //this.FoodImage.setImage(image);
    }
}
