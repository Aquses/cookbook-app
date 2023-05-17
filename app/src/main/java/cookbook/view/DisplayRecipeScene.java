package cookbook.view;

import cookbook.controller.SendRecipeController;
import cookbook.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.Cookbook;

public class DisplayRecipeScene implements Initializable {
    @FXML
    private Text RecipeDetails;
    @FXML
    private Text RecipeIngredients;
    @FXML
    private Text RecipeName;
    @FXML
    private Text RecipeShortDescription;
    @FXML
    private ImageView ReturnButton;
    @FXML
    private Text ServingsText;
    @FXML
    private Text TimeCookText;
    @FXML
    private Text TimePrepareText;
    @FXML
    private AnchorPane ap;
    private Recipe recipe;
    private AnchorPane parentAnchorPane;

    @FXML
    private Button addButton;

    @FXML
    private Button addPlan;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> dayBox;

    @FXML
    private Label dayLabel;

    @FXML
    private ChoiceBox<WeeklyDinnerList> weekBox;

    @FXML
    private Label weekLabel;

    @FXML
    private Button sendRecipe;

    // for vic
    @FXML
    private Button EditRecipeButton;
    @FXML
    private Button FavouriteRecipeButton;
    @FXML
    private ImageView FavButtonIcon;

		private User user;

		private ObservableList<WeeklyDinnerList> weeklyList;

		private String[] days = {"Monday", "Tuesday", "Wednesday", 
			"Thursday", "Friday", "Saturday", "Sunday"};

    private int recipe_id;

    @Override
    public void initialize(URL location, ResourceBundle resources){
			user = Session.getCurrentUser();
			dayBox.setItems(FXCollections.observableArrayList(days));

			try {
				QueryMaker queryMaker = new QueryMaker();
				weeklyList = queryMaker.retrieveWeeklyListObjects(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
  
      weekBox.setItems(weeklyList);
      weekBox.setConverter(new StringConverter<WeeklyDinnerList>() {
          @Override
          public String toString(WeeklyDinnerList week) {
            return week != null && week.getWeekName() != null ? week.getWeekName() : "";
          }
  
          @Override
          public WeeklyDinnerList fromString(String string) {
              return null; 
          }
      }); 

	
      weekLabel.setVisible(false);
      dayLabel.setVisible(false);
      weekBox.setVisible(false);
      dayBox.setVisible(false);
      addButton.setVisible(false);
      cancelButton.setVisible(false);

      FavouriteRecipeButton.setOnMouseClicked(event -> {
        User user = Session.getCurrentUser();
        int user_id = user.getUserId();

          // check weather it is a favorite recipe or not
        DataQuery db = new DataQuery();
        boolean fav = false;
        try {
          fav = db.isFavorite(user_id, recipe_id);
        } catch (SQLException e) {}


        // set icon
        Image image;
        db = new DataQuery();
        if(!fav) {
          db.insertFavorite(user_id, recipe_id);
          image = new Image(getClass().getResource("/menuIcons/star-gold.png").toExternalForm());
        }else {
          db.removeFavorite(user_id, recipe_id);
          image = new Image(getClass().getResource("/menuIcons/star.png").toExternalForm());
        }
          FavButtonIcon.setImage(image);
        });
    }

    public void addRecipeObject(Recipe recipe, AnchorPane parentAnchorPane){
        this.recipe = recipe;
        this.parentAnchorPane = parentAnchorPane;

        RecipeName.setText(recipe.getName());
        RecipeShortDescription.setText(recipe.getDescription());
        RecipeDetails.setText(recipe.getInstructions());
        ServingsText.setText(String.valueOf(recipe.getServings()));

        // Uncomment below if recipe class prep time and cook time attributes are float type
        // TimePrepareText.setText(floatToMinutes(recipe.getPrepTime()));
        // TimeCookText.setText(floatToMinutes(recipe.getCookTime()));

        // For int type below
        TimePrepareText.setText(String.valueOf(recipe.getPrepTime()));
        TimeCookText.setText(String.valueOf(recipe.getCookTime()));

        User user = Session.getCurrentUser();
        int user_id = user.getUserId();
        int recipe_id = recipe.getId();
        this.recipe_id = recipe_id;
        // check weather it is a favorite recipe or not
        DataQuery db = new DataQuery();
        boolean fav = false;
        try {
            fav = db.isFavorite(user_id, recipe_id);
        } catch (SQLException e) {}

        // set icon
        Image image;
        if(fav) {
            image = new Image(getClass().getResource("/menuIcons/star-gold.png").toExternalForm());
        }else {
            image = new Image(getClass().getResource("/menuIcons/star.png").toExternalForm());
        }
        FavButtonIcon.setImage(image);

        return;
    }

    public void addIngredients() {
        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Ingredient> ingredientsList = qm.retrieveIngredients(recipe.getId());
            StringBuilder sb = new StringBuilder();
            
            for (Ingredient i : ingredientsList) {
                sb.append(i.getIngredientName() + " | " + i.getQty() + " | " + i.getMeasurement() + "\n");
            }

            RecipeIngredients.setText(sb.toString());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Below method is used if the prep time and cook time attributes are float
    public String floatToMinutes(float time){
        String t = Float.toString(time);
        float remainder = (time * 60) % 60;

        if(remainder == 0){
            int place = t.indexOf(".");
            String subT = t.substring(0, place);
            return subT + " min";
        } else {
            float seconds = remainder * 60;
            int place = t.indexOf(".");
            String subT = t.substring(0, place);
            return subT + "min " + seconds + "s";
        }
    }

    @FXML
    private void transitionEditScene(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Cookbook.class.getResource("RecipeEditor.fxml"));
        RecipeEditor editor;
        Node n;

        System.out.println(Cookbook.class.getResource("DisplayRecipeScene.fxml"));


        // load first
        try {
            n = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // then get controller
        editor = fxmlLoader.getController();
        editor.initialize(recipe);

        AnchorPane.setTopAnchor(n, 0.0);
        AnchorPane.setRightAnchor(n, 0.0);
        AnchorPane.setBottomAnchor(n, 0.0);
        AnchorPane.setLeftAnchor(n, 0.0);

        parentAnchorPane.getChildren().clear();
        parentAnchorPane.getChildren().add(n);

    }
    @FXML
    void sendRecipe(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(Cookbook.class.getResource("SendRecipe.fxml"));
        Parent sendRecipeRoot = loader.load();
        SendRecipeController sendController = loader.getController();

        sendController.setRecipe(recipe);
                
        Scene sendRecipeScene = new Scene(sendRecipeRoot);
        Stage sendRecipeStage = new Stage();
        sendRecipeStage.setScene(sendRecipeScene);
        sendRecipeStage.showAndWait();
    }

    @FXML
    void add(ActionEvent event) {
			String day = dayBox.getValue();
			WeeklyDinnerList week = weekBox.getValue();
			if (day != null && week != null) {
					try {
						QueryMaker queryMaker = new QueryMaker();
						queryMaker.insertDailyRecipe(week.getWeekId(), day, recipe_id);
						cancel(event);	
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
    
    @FXML
    void addPlan(ActionEvent event) {
    	weekLabel.setVisible(true);
    	dayLabel.setVisible(true);
    	weekBox.setVisible(true);
    	dayBox.setVisible(true);
    	addButton.setVisible(true);
    	cancelButton.setVisible(true);
    }

    @FXML
    void cancel(ActionEvent event) {
    	weekLabel.setVisible(false);
    	dayLabel.setVisible(false);
    	weekBox.setVisible(false);
    	dayBox.setVisible(false);
    	addButton.setVisible(false);
    	cancelButton.setVisible(false);

			dayBox.setValue(null);
			weekBox.setValue(null);
    }
}
