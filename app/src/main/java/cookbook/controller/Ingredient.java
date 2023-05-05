package cookbook.controller;

public class Ingredient {
    private String ingredientName;
    private int recipeId;
    private int qty;
    private String measurement;

    
    
    public Ingredient(String ingredientName, int recipeId, int qty, String measurement) {
        this.ingredientName = ingredientName;
        this.recipeId = recipeId;
        this.qty = qty;
        this.measurement = measurement;
    }

    public Ingredient(String ingredientName, int qty, String measurement) {
        this.ingredientName = ingredientName;
        this.qty = qty;
        this.measurement = measurement;
    }


    public String getIngredientName() {
        return ingredientName;
    }


    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }


    public int getRecipeId() {
        return recipeId;
    }


    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public int getQty() {
        return qty;
    }


    public void setQty(int qty) {
        this.qty = qty;
    }


    public String getMeasurement() {
        return measurement;
    }


    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getName() {
        return null;
    }

    public int getQuantity() {
        return 0;
    }

    

    
    
}
