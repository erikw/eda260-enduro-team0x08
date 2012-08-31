package gui;

import generator.AbstractGenerator;
import generator.MarathonGenerator;
import generator.sorter.Sorter;
import gui.Buttons.AbstractRegisterViewButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import model.Model;

import common.EnduroTime;
import common.ErrorMessage;

import driver.AbstractDriver;
import driver.MarathonDriver;
import driver.factory.DriverFactory;

/**
 * A template for a register view.
 */
@SuppressWarnings("serial")
public abstract class RegisterViewTemplate extends JFrame implements Observer {
	/* The model to use. */
	protected Model<? extends AbstractDriver> model;

	/* Text field to enter start number. */
	private JTextField textfield;

	/* The table model to store data in. */
	private DefaultTableModel tableModel;

	/* The table to present data in. */
	private JTable table;

	/* The generator to use for result generation. */
	private AbstractGenerator generator;

	/* Variable to determine if human edit */
	private boolean isHuman = true;

	private int invalidStNrCounter;

	/**
	 * Instantiate a template class for the register Start and finish view
	 * pages.
	 * 
	 * @param frameName
	 *            The name of the frame.
	 * @param columnNames
	 *            The names of the columns in the table.
	 * @param model
	 *            The model to use.
	 * @param driverFactory
	 *            The factory to use.
	 */
	public RegisterViewTemplate(String frameName, String[] columnNames,
			Model<? extends AbstractDriver> model,
			DriverFactory<? extends AbstractDriver> driverFactory) {
		super(frameName);
		setResizable(false);

		// Konfig
		generator = new MarathonGenerator("persons.txt",
				new Sorter<MarathonDriver>());
		// Konfig

		this.model = model;
		model.addObserver(this);
		
		invalidStNrCounter = -1*model.getUnregisteredMap().size()-1;
		Font font = new Font("Monospaced", Font.BOLD, 30);

		JLabel label = new JLabel("Start Nr.");
		label.setFont(font);
		textfield = new RegisterStartField(this);
		textfield.setFont(font);

		// table
		tableModel = new DisableColumnModel(columnNames, 0);
		tableModel.setColumnCount(4);
		table = new JTable(tableModel);
		table.getTableHeader().setFont(font);
		table.setFont(font);
		table.setRowHeight(32);
		// Hide first column, its only used for data
		table.removeColumn(table.getColumnModel().getColumn(0));

		// Registration button
		RegistrateButton registrateButton = new RegistrateButton(this);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(label);
		topPanel.add(textfield);
		topPanel.add(registrateButton);
		opAddExtraButton(topPanel);

		// Handles mouseclicks on the delete column
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2
						&& table.columnAtPoint(e.getPoint()) == 2) {
					int row = table.rowAtPoint(e.getPoint());
					int decision = JOptionPane.showConfirmDialog(null,
							"Är du säker?", "Bekräfta",
							JOptionPane.OK_CANCEL_OPTION);
					if (decision == JOptionPane.CANCEL_OPTION
							|| decision == JOptionPane.CLOSED_OPTION) {
						return;
					} else if (decision == JOptionPane.OK_OPTION) {
						tableModel.removeRow(row);
					}
				}
			}
		});

		// Called on insert and after edit
		tableModel.addTableModelListener(new tblMdlListner(driverFactory));

		this.add(topPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.pack();
		this.setSize(800, 600);
		this.setVisible(true);

		// Repaint
		update(null, null);
	}

	/**
	 * Class to disable all but first column
	 */
	private class DisableColumnModel extends DefaultTableModel {

		public DisableColumnModel(String[] columnNames, int row) {
			super(columnNames, row);
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex < 2;
		}
	}

	/**
	 * Class that monitors the table for changes
	 */
	private class tblMdlListner implements TableModelListener {
		private DriverFactory<? extends AbstractDriver> driverFactory;

		public tblMdlListner(
				DriverFactory<? extends AbstractDriver> driverFactory) {
			this.driverFactory = driverFactory;
		}

		public void tableChanged(TableModelEvent e) {
			if (isHuman) {
				int editedRow = e.getFirstRow();
				// get stNr from tbl
				int startNbrOld = 0;
				int startNbr = 0;
				startNbrOld = Integer.parseInt(tableModel.getValueAt(editedRow, 0)
						.toString());
				
				try {
					startNbr = Integer.parseInt(tableModel.getValueAt(editedRow, 1)
							.toString());
				} catch (NumberFormatException ex) {
					if(!tableModel.getValueAt(editedRow, 1)
							.toString().isEmpty()){
					ErrorMessage.error(ex, "Invalid startnumber");
					tableModel.setValueAt(startNbrOld, editedRow, 1);
					}
				}
				// get time from tbl
				String time = (String) tableModel.getValueAt(editedRow, 2);

				// Vad är det här för condition? Kommentera gärna...
				if (!(startNbrOld > 0 && startNbr == 0)) {
					AbstractDriver driver = model.getDriver(startNbrOld);
					
					boolean driverExists = model.getMap().containsKey(startNbrOld);
					boolean driverUnregExists = model.getUnregisteredMap().containsKey(startNbrOld);
					
					if (startNbrOld < 0) {
						// edited row with missing id, change stNr
						driver = driverFactory.make(startNbrOld); 
						model.putUnregisteredDriver(driver);
						model.change(startNbrOld, startNbr);
						
						opAddTime(driver, time);
						
					
					} else if (!driverExists && !driverUnregExists) {
						driver = driverFactory.make(startNbrOld);
						opAddTime(driver, time);
//						model.addUnregisteredDriver(driver);
						model.put(startNbrOld, driver);
					} else if (driverUnregExists) {
						driver = model.getUnregisteredMap().get(startNbrOld);
						opAddTime(driver, time);
					} else if (driver != null && startNbrOld == startNbr) {
						// new row
						opAddTime(driver, time);
					} else {
						// edited row with valid stNr, move time between drivers
						opChangeTime(startNbrOld, startNbr, new EnduroTime(time));
					}
				}
			}
		}
	}

	/**
	 * Handles insert from the field to Output file and table.
	 */
	public void enterField() {
		String id = textfield.getText();
		textfield.setText("");

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date).replace(':', '.');

		int stNr = 0;
		try {
			stNr = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			if (id.equals("")) {
				stNr = invalidStNrCounter;
				invalidStNrCounter--;
			} else {
				ErrorMessage.error(e, "DriverID is not an integer");
				return;
			}
		}

		Object[] row = new Object[4];
		row[0] = stNr; // hidden coloumn
		if (stNr > 0){
			row[1] = stNr;
		}else{
			row[1] = "";
		}
		row[2] = time;
		row[3] = "XXXX";
		tableModel.insertRow(0, row);

		textfield.requestFocus();
	}

	/**
	 * Model is changed, update the view.
	 */
	final public void update(Observable o, Object arg) {
		// repaint(model.getGuiVector());
		HashMap<Integer, ? extends AbstractDriver> map = model.getMap();
		List<GuiRow> list = new ArrayList<GuiRow>();

		for (Entry<Integer, ? extends AbstractDriver> ent : map.entrySet()) {
			AbstractDriver ad = ent.getValue();
			List<EnduroTime> times = opGetTime(ad);
			for (EnduroTime t : times) {
				list.add(new GuiRow(ad.getStartNumber(), t));
			}
		}
		
		for (Entry<Integer, ? extends AbstractDriver> ent : model.getUnregisteredMap().entrySet()) {
			AbstractDriver ad = ent.getValue();
			List<EnduroTime> times = opGetTime(ad);
			for (EnduroTime t : times) {
				list.add(new GuiRow(ad.getStartNumber(), t));
			}
		}
		

		Collections.sort(list);

		repaint(list);
	}

	private void repaint(List<GuiRow> list) {
		isHuman = false;
		tableModel.setRowCount(0);
		table.removeAll();

		Object[] row = new Object[4];

		for (GuiRow gr : list) {
			int stNr = gr.getStartNumber();
			row[0] = stNr; // hidden coloumn
			if (stNr > 0){
				row[1] = stNr;
			}else{
				row[1] = "";
			}
			row[2] = gr.getTime();
			row[3] = "XXXX";
			tableModel.addRow(row);
		}

		table.requestFocusInWindow();

		opGenerate(generator);
		isHuman = true;
	}

	private class GuiRow implements Comparable<Object> {
		private int stNr;
		private EnduroTime time;

		public GuiRow(int stNr, EnduroTime time) {
			this.stNr = stNr;
			this.time = time;
		}

		public int getStartNumber() {
			return this.stNr;
		}

		public String getTime() {
			return time.toString();
		}

		public int compareTo(Object o) {
			GuiRow rhs = (GuiRow) o;
			return -1 * this.time.compareTo(rhs.time);
		}
	}

	/**
	 * Uses template method the add a file to the driver
	 * 
	 * @param driver
	 *            Current driver
	 * @param time
	 *            Time to enter
	 */
	public abstract void opAddTime(AbstractDriver driver, String time);

	/**
	 * Method for adding an extra button (the "start all" button in the
	 * StartTimeView)
	 * 
	 * @param topPanel
	 *            the panel where you want to add the button
	 */
	protected abstract void opAddExtraButton(JPanel topPanel);

	/**
	 * Uses template method to save to correct file
	 * 
	 * @param generator
	 *            Generator to be used
	 */
	public abstract void opGenerate(AbstractGenerator generator);

	public abstract List<EnduroTime> opGetTime(AbstractDriver ad);

	/**
	 * The registration button at the RegisterStartTimeView and the
	 * RegisterFinishTimeView.
	 */
	public class RegistrateButton extends AbstractRegisterViewButton {
		/* The parent of the button. */
		private RegisterViewTemplate parent;

		/**
		 * Instantiate the registration button.
		 * 
		 * @param parent
		 *            The parent view.
		 */
		public RegistrateButton(RegisterViewTemplate parent) {
			super("Registrera");
			this.parent = parent;
		}

		/**
		 * Enter field over at parent when button is activated.
		 * 
		 * @param e
		 *            The action event.
		 */
		public void actionPerformed(ActionEvent e) {
			parent.enterField();
		}

	}

	//TODO: Vad är det här för metod?
	/**
	 * 
	 * @param oldD
	 * @param newD
	 * @param time
	 */
	protected abstract void opChangeTime(int oldD, int newD, EnduroTime time);
}