package model;

import java.util.ArrayList;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class Product {
    private int sku;
    private String name;
    private double salesPrice;
    private String productType;
    private ArrayList<Ingredient> ingredients;

    /**
     * Constructs a Product object with the specified values.
     *
     * @param sku         The Stock Keeping Unit (SKU) identifying the product.
     * @param name        The name of the product.
     * @param salesPrice  The price at which the product is sold.
     * @param productType The type/category of the product (Input is either Sandwich or Rundstykke).
     */
    public Product(int sku, String name, double salesPrice, String productType) {
        this.sku = sku;
        this.name = name;
        this.salesPrice = salesPrice;
        if ("sandwich".equalsIgnoreCase(productType.toLowerCase().trim()) || "rundstykke".equalsIgnoreCase(productType.toLowerCase().trim())) {
            this.productType = productType;
        } else {
            throw new IllegalArgumentException("Produkt type kan kun være Sandwich eller Rundstykke");
        }
    }

    /**
     * Gets the Stock Keeping Unit (SKU) of the product.
     *
     * @return The SKU of the product.
     */
    public int getSku() {
        return sku;
    }

    /**
     * Sets the Stock Keeping Unit (SKU) of the product.
     *
     * @param sku The new SKU for the product.
     */
    public void setSku(int sku) {
        this.sku = sku;
    }

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The new name for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the sales price of the product.
     *
     * @return The sales price of the product.
     */
    public double getSalesPrice() {
        return salesPrice;
    }

    /**
     * Sets the sales price of the product.
     *
     * @param salesPrice The new sales price for the product.
     */
    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * Gets the type/category of the product.
     *
     * @return The product type.
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the type/category of the product.
     * The input can only be one of the two values: "Sandwich" eller "Rundstykke"
     * @param productType The new product type.
     */
    public void setProductType(String productType) {
    	if ("sandwich".equalsIgnoreCase(productType.toLowerCase().trim()) || "rundstykke".equalsIgnoreCase(productType.toLowerCase().trim())) {
            this.productType = productType;
        } else {
            throw new IllegalArgumentException("Produkt type kan kun være Sandwich eller Rundstykke");
        }
    }
}
