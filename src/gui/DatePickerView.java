/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dataaccesslayer.DataAccessException;
import model.Employee;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.awt.event.ActionEvent;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class DatePickerView extends JFrame {
	private UtilDateModel dateModel;
	private Date currentDate;
	private Date desiredDate;
	private LocalDateTime desiredDateConverted;
	private LocalDateTime currentDateConverted;
	private Employee employee;

	/**
	 * 
	 */
	public DatePickerView(Employee employee) {
		this.employee = employee;
		setTitle("Vælg dato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnCreateOrder = new JButton("Opret ordre");
		btnCreateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createOrderClicked();
				} catch (DataAccessException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCreateOrder.setBounds(151, 227, 101, 23);
		panel.add(btnCreateOrder);

		JButton btnCancel = new JButton("Tilbage");
		btnCancel.setBounds(250, 227, 101, 23);
		panel.add(btnCancel);

		JLabel lblNewLabel = new JLabel("Vælg dato");
		lblNewLabel.setBounds(151, 57, 89, 14);
		panel.add(lblNewLabel);

		dateModel = new UtilDateModel();
		dateModel.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(151, 75, 200, 28);
		panel.add(datePicker);

		init();

		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private void init() {
		setData();
	}

	/**
	 * 
	 */
	private void setData() {
		// Current date and time
		currentDate = new Date();
		currentDateConverted = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Set DatePickers start date
		dateModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH) + 2);

	}

	/**
	 * Retrives date of DatePicker from user
	 */
	public LocalDateTime getDateFromCalender() {
		// Retrieve value from calendar
		desiredDate = dateModel.getValue();
		desiredDateConverted = LocalDateTime.ofInstant(desiredDate.toInstant(), ZoneId.systemDefault());
		
		return desiredDateConverted;
	}
	/**
	 * Handles operations when create order is clicked
	 * @throws DataAccessException 
	 * 
	 */
	public void createOrderClicked() throws DataAccessException {
		// Retrieve value from calendar
		getDateFromCalender();
		openProductView();
		clearWindow();
		//TODO Logic for check of desiredDate - currentDate >= 2
	}
	/**
	 * Opens a ProductView with the two dates, creation date and desired date
	 * @throws DataAccessException 
	 */
	public void openProductView() throws DataAccessException {
		ProductView productView = new ProductView(currentDateConverted, desiredDateConverted, employee);
		productView.setVisible(true);
	}

	/**
	 * Closes current window
	 */
	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}
}
