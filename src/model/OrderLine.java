/**
 * 
 */
package model;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class OrderLine {
    private int orderLineId;
    private int quantity;

    private Product product;

    // Constuctor for OrderLine
    public OrderLine(int quantity, Product product) {
        this.orderLineId = -1; //If -1, then it is not in the database
        this.quantity = quantity;
        this.product = product;
    }

    // Getter and Setter for orderLineId
    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for orderId


    // Getter and Setter for sku
    public Product getProduct() {
    	return product;
    }

    public void setProduct(Product p) {
    	this.product = p;
    }
}
