/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import control.OrderController;
import dataaccesslayer.OrderDB;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ConfirmedOrdersView extends JFrame {
	private JTable tableConfirmed;
	private ConfirmedOrderTableModel confirmedOrderTableModel;
	private OrderController orderController;

	/**
	 * 
	 */
	public ConfirmedOrdersView() {
		orderController = new OrderController(new OrderDB());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bekræftede ordrer");
		setBounds(100, 100, 500, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnReadyForPickup = new JButton("Marker klar");
		btnReadyForPickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markReadyForPickupClicked();
			}
		});
		btnReadyForPickup.setBounds(326, 227, 98, 23);
		panel.add(btnReadyForPickup);

		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuClicked();
			}
		});
		btnBackToMenu.setBounds(321, 11, 103, 23);
		panel.add(btnBackToMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 26, 296, 224);
		panel.add(scrollPane);
		
		tableConfirmed = new JTable();
		tableConfirmed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		confirmedOrderTableModel = new ConfirmedOrderTableModel();
		scrollPane.setViewportView(tableConfirmed);
	}

	/**
	 * Go back to menu from ConfirmedOrdersView
	 */
	private void backToMenuClicked() {

		OrderView orderView = new OrderView();
		orderView.run(orderView);
		clearWindow();
	}

	/**
	 * Assign employee to order
	 */
	private void markReadyForPickupClicked() {
		// TODO Change status of instance PickUpStatus on selected Order
		
		// GUI updates to show new state
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
		orderController.getOrdersWithBoolean();
	}
	
}
