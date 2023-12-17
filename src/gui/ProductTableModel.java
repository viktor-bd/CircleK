/**
 * 
 */
package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import model.Product;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ProductTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Product> products;
	private ArrayList<Integer> quantities;
	private static final String[] COLUMN_HEADERS = { "Produkt", "Antal" };

	/**
	 * Constructor, calls AbstractTableModel and inits an ArrayList
	 */
	public ProductTableModel() {
		super();
		this.products = new ArrayList<Product>();
		this.quantities = new ArrayList<Integer>();

	}

	@Override
	public int getRowCount() {
		return products.size();
	}

	@Override
	public String getColumnName(int col) {
		return COLUMN_HEADERS[col];
	}

	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        Product p = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getName();
            case 1:
                if (!quantities.isEmpty() && rowIndex < quantities.size()) {
                    return quantities.get(rowIndex);
                } else {
                    return 0; // Or any default value you want to display
                }
            default:
                return "Unknown column: " + columnIndex + ". Valid is 0-1";
        }
    }

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
	    if (columnIndex == 1) {
	        try {
	            int quantity = Integer.parseInt(value.toString());

	            // Ensure that the quantities list has enough elements
	            while (quantities.size() <= rowIndex) {
	                quantities.add(0); // Or any default value you want
	            }

	            quantities.set(rowIndex, quantity);

	            // Print the updated quantities for debugging
	            System.out.println("Quantities: " + quantities);

	            // Notify the table of the update
	            fireTableCellUpdated(rowIndex, columnIndex);
	        } catch (NumberFormatException e) {
	            // Handle invalid input (non-integer) if needed
	        }
	    }
	}
	 // Override the getEditor method to use a text field editor for the Quantity column
 
	public List<Product> getList() {
		return products;
	}
	public List<Integer> getIntList() {
		return quantities;
	}

	public Product getPropositionAtIndex(int index) {
		return products.get(index);
	}

	public void setData(ArrayList<Product> products) {
		this.products = products;
		fireTableDataChanged();
	}
}
