package cookbook.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.management.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class RecipeEditor {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button deleteButton;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea instructionsArea;

    @FXML
    private TextField editCookTimeField;

    @FXML
    private TextField editNameField;

    @FXML
    private TextField editPrepTimeField;

    @FXML
    private TextField editServingsField;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> measurementCol;

    @FXML
    private TableColumn<Ingredient, String> nameCol;


    @FXML
    private TableColumn<Ingredient, Integer> qtyCol;


    @FXML
    private TextField inputMeasurement;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputQty;

    @FXML
    private Button updateButton;

    @FXML
    private Button saveButton;


    ObservableList<Ingredient> ingredientList =  FXCollections.observableArrayList();
    Recipe recipe;
    int recipeId;

    @FXML
    void initialize(Recipe recipe) {
        this.recipe = recipe;
        this.recipeId = recipe.getId();
        loadData();
        refreshTable(recipeId);
        addRecipeObject(recipe);
    }

    public void loadData() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        measurementCol.setCellValueFactory(new PropertyValueFactory<>("measurement"));
    }

    public void refreshTable(int recipeId) {
        // this.recipeId = recipeId;
        ingredientList.clear();

        try {
            QueryMaker qm = new QueryMaker();
            ObservableList<Ingredient> databaseList = qm.retrieveIngredients(recipeId);
            
            for (Ingredient i : databaseList) {
                ingredientList.add(new Ingredient(i.getIngredientName(), i.getQty(), i.getMeasurement()));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        ingredientsTable.setItems(ingredientList);
    }

    @FXML
    private void ingredientClicked(MouseEvent event) {
        Ingredient selectedIngredient = ingredientsTable.getSelectionModel().getSelectedItem();
        inputName.setText(selectedIngredient.getIngredientName());
        inputQty.setText(String.valueOf(selectedIngredient.getQty()));
        inputMeasurement.setText(selectedIngredient.getMeasurement());
    }

    
    @FXML
    private void update(ActionEvent event) {
        Ingredient editedIngredient = ingredientsTable.getSelectionModel().getSelectedItem();
        String originalName = editedIngredient.getIngredientName();
        editedIngredient.setIngredientName(inputName.getText());
        editedIngredient.setQty(Integer.parseInt(inputQty.getText()));
        editedIngredient.setMeasurement(inputMeasurement.getText());

        try {
            QueryMaker qm = new QueryMaker();
            qm.updateIngredient(editedIngredient, originalName, recipeId);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        ingredientsTable.refresh();
        inputName.clear();
        inputQty.clear();
        inputMeasurement.clear();
    }

    @FXML
    private void deleteIngredient(ActionEvent event) {
        Ingredient selectedIngredient = ingredientsTable.getSelectionModel().getSelectedItem();
        ingredientsTable.getItems().remove(selectedIngredient);

        try {
            QueryMaker qm = new QueryMaker();
            qm.deleteIngredient(selectedIngredient, recipeId);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        ingredientsTable.refresh();
        inputName.clear();
        inputQty.clear();
        inputMeasurement.clear();
    }

    @FXML
    private void addIngredient(ActionEvent event) {
        String ingredientName = inputName.getText();
        int id = recipeId;
        int qty = Integer.parseInt(inputQty.getText());
        String measurement = inputMeasurement.getText();

        Ingredient newIngredient = new Ingredient(ingredientName, id, qty, measurement);

        try {
            QueryMaker qm = new QueryMaker();
            qm.addIngredient(newIngredient);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        ingredientList.add(newIngredient);
        ingredientsTable.refresh();
        inputName.clear();
        inputQty.clear();
        inputMeasurement.clear();
    }


    public void addRecipeObject(Recipe recipe){

        // this.recipe = recipe;
        // this.recipeId = recipe.getId();

        editNameField.setText(recipe.getName());
        descriptionArea.setText(recipe.getDescription());
        instructionsArea.setText(recipe.getInstructions());
        editServingsField.setText(String.valueOf(recipe.getServings()));
        editPrepTimeField.setText(String.valueOf(recipe.getPrepTime()));
        editCookTimeField.setText(String.valueOf(recipe.getCookTime()));

    }

    @FXML
    void saveRecipe(ActionEvent event) {
        System.out.println(recipe.getName());
        String savedName = editNameField.getText();
        String savedDescription = descriptionArea.getText();
        String savedInstructions = instructionsArea.getText();
        int savedServings = Integer.parseInt(editServingsField.getText());
        int savedPrepTime = Integer.parseInt(editPrepTimeField.getText());
        int savedCookTime = Integer.parseInt(editCookTimeField.getText());

        System.out.println(savedName);
        recipe.setName(savedName);
        recipe.setDescription(savedDescription);
        recipe.setInstructions(savedInstructions);
        recipe.setServings(savedServings);
        recipe.setPrepTime(savedPrepTime);
        recipe.setCookTime(savedCookTime);

        try {
            QueryMaker qm = new QueryMaker();
            qm.updateRecipe(recipe);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }



}
    
