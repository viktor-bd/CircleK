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
import java.awt.event.ActionEvent;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ConfirmedOrdersView extends JFrame {

	/**
	 * 
	 */
	public ConfirmedOrdersView() {
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

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 10, 297, 241);
		panel.add(scrollPane);

		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuClicked();
			}
		});
		btnBackToMenu.setBounds(321, 11, 103, 23);
		panel.add(btnBackToMenu);
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
}
