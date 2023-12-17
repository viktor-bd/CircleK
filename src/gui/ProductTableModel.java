/**
 * 
 */
package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Product;


/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class ProductTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Product> products;
	private static final String[] COLUMN_HEADERS = {"Produkt"};
	
	/**
	 * Constructor, calls AbstractTableModel and inits an ArrayList	
	 */
	public ProductTableModel() {
		super();
		this.products = new ArrayList<Product>();
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
		String result = "";
		switch(columnIndex) {
		default: 
			result = "Unknown column: " + columnIndex + ". Valid is 0-1";
		case 0:
			result += p.getName();
		}
		return result;
	}
	public List<Product> getList() {
		return products;
	}

	public Product getPropositionAtIndex(int index) {
		return products.get(index);
	}

	public void setData(ArrayList<Product> products) {
		this.products = products;
		fireTableDataChanged();
	}
}
