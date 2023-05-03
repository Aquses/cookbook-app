package cookbook.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Recipe {
    private int id;
    private String name;
    private String imgSrc;
    private String description;
    private String instructions;
    private int servings;
    private float prepTime;
    private float cookTime;

    public Recipe(ResultSet rt) {
        try {
            setId(rt.getInt(1));
            setName(rt.getString(2));
            setDescription(rt.getString(3));
            setInstructions(rt.getString(4));
            setServings(rt.getInt(5));
            setPrepTime(rt.getFloat(6));
            setCookTime(rt.getFloat(7));
            //setImgSrc(); //TODO: Add image source to the recipe in Recipe.java
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public float getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(float prepTime) {
        this.prepTime = prepTime;
    }

    public float getCookTime() {
        return cookTime;
    }

    public void setCookTime(float cookTime) {
        this.cookTime = cookTime;
    }
}
