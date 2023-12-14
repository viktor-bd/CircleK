package model;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class StockLine {
    private int stockLineId;
    private int quantity;
    private Ingredient ingredient;

    // Constructor
    public StockLine(int stockLineId, int quantity, Ingredient ingredient) {
        this.stockLineId = stockLineId;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    // Getter and Setter for stockLineId
    public int getStockLineId() {
        return stockLineId;
    }

    public void setStockLineId(int stockLineId) {
        this.stockLineId = stockLineId;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for ingredient
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}