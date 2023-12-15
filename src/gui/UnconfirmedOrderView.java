/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import control.OrderController;
import dataaccesslayer.DataAccessException;
import dataaccesslayer.OrderDB;
import model.Order;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class UnconfirmedOrderView extends JFrame {
	private JTable tableUnconfirmedOrder;
	private JTable tableUnconfirmedOrders;
	private UnconfirmedOrderTableModel unconfirmedOrderTableModel;
	private OrderController orderController;

	/**
	 * Creates and sets the view
	 * @throws DataAccessException 
	 */
	public UnconfirmedOrderView() throws DataAccessException {
		orderController = new OrderController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bekræft ordrer");
		setBounds(100, 100, 500, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUnconfirmedOrders = new JLabel("Ordrer til godkendelse");
		lblUnconfirmedOrders.setBounds(0, 0, 331, 22);
		panel.add(lblUnconfirmedOrders);
		
		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuClicked();
			}
		});
		btnBackToMenu.setBounds(331, 23, 103, 23);
		panel.add(btnBackToMenu);
		
		JButton btnConfirmOrder = new JButton("Godkend ordre");
		btnConfirmOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmOrderClicked();
			}
		});
		btnConfirmOrder.setBounds(331, 238, 103, 23);
		panel.add(btnConfirmOrder);
		
		JButton btnRejectOrder = new JButton("Afvis ordre");
		btnRejectOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rejectOrderClicked();
			}
		});
		btnRejectOrder.setBounds(331, 214, 103, 23);
		panel.add(btnRejectOrder);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 21, 331, 240);
		panel.add(scrollPane);
		
		tableUnconfirmedOrders = new JTable();
		tableUnconfirmedOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unconfirmedOrderTableModel = new UnconfirmedOrderTableModel();
		// createCustomer
		unconfirmedOrderTableModel.setData(getOrdersFromDB());
		tableUnconfirmedOrders.setModel(unconfirmedOrderTableModel);
		scrollPane.setViewportView(tableUnconfirmedOrders);
		//TODO Thread for update table
		// Setting up a thread for updating
//		exec = Executors.newSingleThreadScheduledExecutor();
//		exec.scheduleAtFixedRate(new Runnable() {
//		    @Override
//		    public void run() {
//		        TableModel.setData(getDataMethod());
//		        TableModel.revalidate();
//		        TableModel.repaint();
//		    }
//		}, 0, 5, TimeUnit.SECONDS);
//		import java.util.concurrent.Executors;
//		import java.util.concurrent.ScheduledExecutorService;
//		import java.util.concurrent.TimeUnit;
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * The selected order will be rejected on click
	 */
	protected void rejectOrderClicked() {
		// TODO Show active order

		// The active order is removed
		
		// GUI updates to show new state
		
	}
	/**
	 * The selected order will be confirmed on click
	 */
	protected void confirmOrderClicked() {
		// TODO Show active order
		
		// The active is order is created using the CreateOrder function
		
		// GUI updates to show new state
		
	}
	/**
	 * Go back to menu from OrderView
	 */
	private void backToMenuClicked() {
		
		OrderView orderView = new OrderView();
			orderView.run(orderView);
			clearWindow();
	}
	/**
	 * Close current window
	 */
	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void display() {
		
	}
	public ArrayList<Order> getOrdersFromDB() {
		return orderController.getOrdersWithBoolean(false);
		
	}
}
