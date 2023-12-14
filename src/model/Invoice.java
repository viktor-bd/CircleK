/**
 * 
 */
package model;

import java.time.LocalDateTime;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */



public class Invoice {
	private Order order;
    private int invoiceNumber;
    private LocalDateTime paymentDate;
    private int amount;

    // Constructor
    public Invoice(Order order, int invoiceNumber, LocalDateTime paymentDate, int amount) {
    	this.order = order;
        this.invoiceNumber = order.getOrderId();
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Getter and Setter for invoiceNumber
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    // Getter and Setter for paymentDate
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Getter and Setter for amount
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Order getOrder() {
    	return order;
    }
    public void setOrder(Order o) {
    	this.order = o;
    }
}