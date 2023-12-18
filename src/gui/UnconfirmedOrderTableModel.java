/**
 * 
 */
package gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import model.Order;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class UnconfirmedOrderTableModel extends AbstractTableModel {

	private ArrayList<Order> orders;
	private static final String[] COLUMN_HEADERS = { "Ordre id #", "Dato", "PickUp Dato", "Kunde", "Pris" };

	/**
	 * 
	 */
	public UnconfirmedOrderTableModel() {
		super();
		this.orders = new ArrayList<Order>();
	}

	@Override
	public String getColumnName(int col) {

		return COLUMN_HEADERS[col];
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}
	// Changed this to not break but all return so we can reach order
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);

		
		switch (columnIndex) {
		default:
			return "Unknown column: " + columnIndex + ". Valid is 0-4";
		case 0:
			return order.getOrderId();

		case 1:
			return order.getDate().toString();

		case 2:
			return order.getPickupDate().toLocalDate();

		case 3:
			return order.getCustomer();

		case 4:
			return order.getPrice();
			

		}
	}

	public ArrayList<Order> getOrders() {
		return this.orders;
	}

	public Order getOrderAtIndex(int index) {
		return orders.get(index);
	}

	public void setData(ArrayList<Order> orders) {
		this.orders = orders;
		super.fireTableDataChanged();
	}
}
