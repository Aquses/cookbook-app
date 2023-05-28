package cookbook.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This object matches the database entry for recipes.
 */
public class Recipe {
  private int id;
  private String name;
  private String imgSrc;
  private String description;
  private String instructions;
  private int servings;

  // Below is for float type
  // private float prepTime;
  // private float cookTime;

  private int prepTime;
  private int cookTime;

  public Recipe(ResultSet rt) {
    try {
      setId(rt.getInt(1));
      setName(rt.getString(2));
      setDescription(rt.getString(3));
      setInstructions(rt.getString(4));
      setServings(rt.getInt(5));

      // Below is for float type
      // setPrepTime(rt.getFloat(6));
      // setCookTime(rt.getFloat(7));

      setPrepTime(rt.getInt(6));
      setCookTime(rt.getInt(7));
      //setImgSrc();
      //tODO: Add image source to the recipe in Recipe.java

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

  // If using float must change the preptime getters and setters to float type
  public int getPrepTime() {
    return prepTime;
  }

  public void setPrepTime(int prepTime) {
    this.prepTime = prepTime;
  }

  // If using float must change the cooktime getters and setters to float type
  public int getCookTime() {
    return cookTime;
  }

  public void setCookTime(int cookTime) {
    this.cookTime = cookTime;
  }

}
