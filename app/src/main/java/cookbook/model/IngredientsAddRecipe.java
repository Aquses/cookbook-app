package cookbook.model;

public class IngredientsAddRecipe {
    
    private String name;
    private int quantity;
    private String measurement;

    public IngredientsAddRecipe(String name, int quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
