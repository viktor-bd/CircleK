package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */

public class Order {
    private int orderId;
    private LocalDateTime date;
    private boolean pickUpStatus;
    private LocalDateTime pickupDate;
    private boolean isPaid;
    private Customer customer;
    private Employee employee;
    private ArrayList<OrderLine> orderLines;
    private static final int minimumProducts = 20; // Minimum number of products to order as per special requirement skal det være her eller i Order?

    /**
     * TODO Check if orderID should be initialized in constructor or set on confirmation of order
     * Constructs an Order object with the specified values.
     *
     * @param orderId     The unique numeric identifier for the order.
     * @param date        The date when the order was created.
     * @param pickUpStatus The status of the order for pickup (true if ready, false if not ready). This is only for Circle K mostly (whether or not it needs to be handled)
     * @param pickupDate  The date the customer requested the order be to be ready
     * @param isPaid      The status of the order, whether its paid or not. True if paid, false if non-paid.
     * @param customerId  The unique identifier of the customer associated with the order. 
     * @param employeeId  The unique identifier of the employee associated with handling the order. 
     */
    public Order(int orderId, boolean pickUpStatus, LocalDateTime pickupDate,
                 boolean isPaid, Customer customer, Employee employee) {
        this.orderId = -1; // Hardcoding -1 to differentiate between non persisted (-1) orders and persisted orders (non negative, non null id)
        this.date = LocalDateTime.now();
        this.pickUpStatus = pickUpStatus; // TODO False from creation, manually 
        this.pickupDate = pickupDate;
        this.isPaid = isPaid;
        this.customer = customer;
        this.employee = employee;
        orderLines = new ArrayList<OrderLine>();
    }
    
    /**
	 * Returns the current time in the specified format.
	 * FIXME Separate class?
	 * @return the formatted current time
	 */
	private String getTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime;
	}
    /**
     * Gets the unique identifier for the order.
     *
     * @return The order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier for the order.
     *
     * @param orderId The new order ID.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the date when the order was created.
     *
     * @return The order creation date.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date when the order was created.
     *
     * @param date The new order creation date.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns the status of the order for pickup.
     *
     * @return true if the order is ready for pickup, false if not ready.
     */
    public boolean isPickUpStatus() {
        return pickUpStatus;
    }

    /**
     * Sets the status of the order for pickup.
     *
     * @param pickUpStatus The new status for pickup (true if ready, false if not ready).
     */
    public void setPickUpStatus(boolean pickUpStatus) {
        this.pickUpStatus = pickUpStatus;
    }

    /**
     * Gets the date when the customer plans to pick up the order.
     *
     * @return The planned pickup date.
     */
    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    /**
     * Sets the date when the customer plans to pick up the order.
     *
     * @param pickupDate The new planned pickup date.
     */
    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    /**
     * Returns the payment status of the order.
     *
     * @return true if the order is paid, false if not paid.
     */
    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Sets the payment status of the order.
     *
     * @param paid The new payment status (true if paid, false if not paid).
     */
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    /**
     * Gets the unique identifier of the customer associated with the order.
     *
     * @return The customer of the order.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the unique identifier of the customer associated with the order.
     *
     * @param The customer to be added
     */
    public void setCustomer(Customer c) {
        this.customer = c;
    }

    /**
     * Gets the unique identifier of the employee associated with handling the order.
     *
     * @return The employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the unique identifier of the employee associated with handling the order.
     *
     * @param The employee creating the order
     */
    public void setEmployeeId(Employee employee) {
        this.employee = employee;
    }
    /**
     * Adds orderLines to Order
     * @param the orderLine to be added
     */
    public void addOrderLine(OrderLine ol) {
    	orderLines.add(ol);
    }
    
    public int getSizeOfOrderLines() {
    	return orderLines.size();
    }
    

}
