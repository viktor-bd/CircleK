/**
 * 
 */
package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import control.OrderController;
import model.Order;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class UnconfirmedOrderTableModel extends AbstractTableModel {
	
	private OrderController orderController;
	private ArrayList<Order> orders;
	private static final String[] COLUMN_HEADERS = {"Ordre id #", "Dato",
			"PickUp Dato", "Kunde", "Pris"};
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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);
		
		String result = "";
		switch (columnIndex) {
		default:
			result = "Unknown column: " + columnIndex + ". Valid is 0-4";
		case 0:
			result += order.getOrderId();
			break;
		case 1:
			result = order.getDate().toString();
			break;
		case 2:
			result += order.getPickupDate().toLocalDate();
			break;
		case 3: 
			result += "Kunde"; //TODO customer.firstname whatever
			break;
		case 4: 
			result += "Pris"; //TODO
			break;
		
		}
		return result;
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


