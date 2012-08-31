package gui;

import generator.AbstractGenerator;

import java.util.List;

import javax.swing.JPanel;

import model.Model;

import common.EnduroTime;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * The view for registering finish times.
 */
@SuppressWarnings("serial")
public class RegisterFinishTimeView extends RegisterViewTemplate {
	/* The column names in the table. */
	private static String[] columnNames = { "old", "Startnummer", "Sluttid", "Ta Bort" };

	/**
	 * Instantiate the view.
	 * 
	 * @param model
	 *            The model to use.
	 */
	public RegisterFinishTimeView(Model<? extends AbstractDriver> model, DriverFactory<? extends AbstractDriver> driverFactory) {
		super("Registrerar Sluttid", columnNames, model, driverFactory);
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
		driver.setFinishTime(new EnduroTime(time));
	}

	/**
	 * Uses template method to save to correct file
	 * 
	 * @param generator
	 *            Generator to be used
	 */
	public void opGenerate(AbstractGenerator generator) {
		generator.generateFinish(model);
	}

	/**
	 * A method for getting the finsihTime
	 * 
	 * @param ad
	 *            , the driver you want the time for
	 */
	public List<EnduroTime> opGetTime(AbstractDriver ad) {
		return ad.getFinishTimes();
	}

	/**
	 * A method for putting an button ONLY at the this Time View
	 * 
	 * @param topPanel
	 *            , the panel which the button will appear
	 */
	protected void opAddExtraButton(JPanel topPanel) {
		// No extra button yet
	}

	@Override
	protected void opChangeTime(int oldD, int newD, EnduroTime time) {
		model.moveTimeFinish(oldD, newD, time);
		
	}

}
