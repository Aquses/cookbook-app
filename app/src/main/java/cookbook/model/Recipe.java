package cookbook.model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
  private int prepTime;
  private int cookTime;

  /**
   * This constructor uses a query result to initialize itself.
   *
   * @param rt SQL query result.
   */
  public Recipe(ResultSet rt) {
    try {
      setId(rt.getInt(1));
      setName(rt.getString(2));
      setDescription(rt.getString(3));
      setInstructions(rt.getString(4));
      setServings(rt.getInt(5));
      setPrepTime(rt.getInt(6));
      setCookTime(rt.getInt(7));
      //setImgSrc();

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
