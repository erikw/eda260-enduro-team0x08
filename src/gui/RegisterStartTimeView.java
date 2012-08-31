package gui;

import generator.AbstractGenerator;
import gui.Buttons.AbstractRegisterViewButton;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JPanel;

import model.Model;

import common.EnduroTime;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * The view for registering start times.
 */
@SuppressWarnings("serial")
public class RegisterStartTimeView extends RegisterViewTemplate {
	/* The column names for the table. */
	private static String[] columnNames = { "old" ,"Startnummer", "Starttid",
			"Ta bort" };

	// private JButton startall;

	/**
	 * Instantiate a register view for start times.
	 * 
	 * @param model
	 *            The model to use.
	 */
	public RegisterStartTimeView(Model<? extends AbstractDriver> model,
			DriverFactory<? extends AbstractDriver> driverFactory) {
		super("Registrerar Starttid", columnNames, model, driverFactory);
	}


	/**
	 * Uses template method the add a file to the driver
	 * 
	 * @param driver
	 *            Current driver
	 * @param time
	 *            Time to enter
	 */
	public void opAddTime(AbstractDriver driver, String time) {
		driver.setStartTime(new EnduroTime(time));
	}

	/**
	 * Uses template method to save to correct file
	 * 
	 * @param generator
	 *            Generator to be used
	 */
	public void opGenerate(AbstractGenerator generator) {
		generator.generateStart(model);
	}

	/**
	 * Get the times to print
	 */
	public List<EnduroTime> opGetTime(AbstractDriver ad) {
		return ad.getStartTimes();
	}

	/**
	 * A method for putting an button ONLY at the this Time View
	 * 
	 * @param topPanel
	 *            , the panel which the button will appear
	 */
	protected void opAddExtraButton(JPanel topPanel) {
		StartAllButton startallButton = new StartAllButton(model);
		topPanel.add(startallButton);
	}

	// ------------------------- Button Classes:
	// ------------------------------------
	/**
	 * The "start all" button at the RegisterStartTimeView and the
	 * RegisterFinishTimeView
	 */
	public class StartAllButton extends AbstractRegisterViewButton {
		/* The model to use. */
		private Model<? extends AbstractDriver> model;

		// private RegisterViewTemplate parent; Ska den ha en parent? Ska den
		// här
		// knappen ens finnas på regFinishTimeView?

		/**
		 * Instantiate the start all button.
		 * 
		 * @param model
		 *            The model to use.
		 */
		public StartAllButton(Model<? extends AbstractDriver> model) {
			super("Starta alla!");
			this.model = model;
		}

		/**
		 * Generate mass start when button is activated.
		 * 
		 * @param e
		 *            The action event.
		 */
		public void actionPerformed(ActionEvent e) {
//			model.generateMassStart(new EnduroTime().toString());

			new MassStartDialog(model);
		}

	}
	// ---------------------------------------------------------------------

	@Override
	protected void opChangeTime(int oldD, int newD, EnduroTime time) {
		model.moveTimeStart(oldD, newD, time);
	}
}