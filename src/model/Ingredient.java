/**
 * 
 */
package model;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class Ingredient {
    private int ingredientId;
    private String name;
    private double purchasePrice;
    private Supplier supplier;

	// Constructor: TODO again check ingredientid
    public Ingredient(int ingredientId, String name, double purchasePrice, Supplier supplier) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.supplier = supplier;
    }

   
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 
    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}