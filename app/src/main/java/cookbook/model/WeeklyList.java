package cookbook.model;

import java.util.ArrayList;

public class WeeklyList {
    private ArrayList<Recipe> recipes;
    private ArrayList<String> days;
    
    public WeeklyList(ArrayList<Recipe> recipes, ArrayList<String> days) {
        this.recipes = recipes;
        this.days = days;
    }
    
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
    
    public ArrayList<String> getDays() {
        return days;
    }
    
    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
    
    public void addRecipe(Recipe recipe, String day) {
        recipes.add(recipe);
        days.add(day);
    }
}
