/**
 * 
 */
package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.OrderLine;
import model.Product;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class ProductOrderLinesTableModel extends AbstractTableModel {
	private ArrayList<OrderLine> orderLines;
	private static final String[] COLUMN_HEADERS = { "Produkt", "Antal" };
	
	public ProductOrderLinesTableModel() {
		super(); 
		this.orderLines = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return orderLines.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_HEADERS.length;
	}
	@Override
	public String getColumnName(int col) {
		return COLUMN_HEADERS[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Product p = orderLines.get(rowIndex).getProduct();
		int i = orderLines.get(rowIndex).getQuantity();
        switch (columnIndex) {
            case 0:
                return p.getName();
            case 1:
                return i;
            default:
                return "Unknown column: " + columnIndex + ". Valid is 0-1";
        }
    }
	
	public OrderLine getPropositionAtIndex(int index) {
		return orderLines.get(index);
	}

	public void setData(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
		fireTableDataChanged();
	}

}
