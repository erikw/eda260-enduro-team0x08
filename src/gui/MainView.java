package gui;

import static common.ErrorMessage.error;
import generator.AbstractGenerator;
import generator.sorter.Sorter;
import gui.Buttons.AbstractMainViewButton;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import parser.FinishParser;
import parser.PersonParser;
import parser.StartParser;

import common.Configuration;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * The main view for the program. Main method repose here.
 * 
 */
@SuppressWarnings("serial")
public class MainView extends JFrame {

	/* The model to use. */
	private Model<? extends AbstractDriver> model;
	private Sorter<? extends AbstractDriver> sorter;
	private DriverFactory<? extends AbstractDriver> driverFactory;

	/**
	 * Main method of the program
	 * 
	 * @param args
	 *            None so far.
	 */
	public static void main(String[] args) {
		new MainView().setVisible(true);
	}

	/**
	 * The start view (home page).
	 */

	public MainView() {
		super("Home");
		setResizable(false);
		setUndecorated(false);

		// Läs i från conf-fil och instantiera med rätt typparameter.
		// model = new Model<LapDriver>();
		// sorter = new Sorter<LapDriver>();

		model = Configuration.model;
		sorter = Configuration.sorter;

		driverFactory = Configuration.factory;

		try {
			PersonParser pp = new PersonParser(Configuration.nameFile,
					driverFactory);
			pp.parse(model);
		} catch (FileNotFoundException e) {
			error(e,
					"Couldn't find file: "
							+ Configuration.nameFile
							+ "This is impossible as it is created by a generator instantiated in Configuration");
		} catch (IOException e) {
			error(e, "Couldn't read file: " + Configuration.nameFile);
		}

		try {
			if (new File(Configuration.startFile).exists()) {
				StartParser sp = new StartParser(Configuration.startFile,
						Configuration.factory);
				sp.parse(model);
			}
			if (new File(Configuration.finishFile).exists()) {
				FinishParser fp = new FinishParser(Configuration.finishFile,
						Configuration.factory);
				fp.parse(model);
			}
		} catch (IOException e) {
			error(e, "Problem reading start or/and finish file(s)");
		}

		/*
		 * Lägg till homePanel i fall att vi ska ha alla sidor i samma fönster
		 * och sedan avnända oss av "Back"-knappar som tar oss tillbaka till
		 * Home
		 * 
		 * JPanel homePanel = new JPanel();
		 * 
		 * JLabel headerLabel = new JLabel("Home"); headerLabel.setFont(new
		 * Font("Dialog", Font.BOLD, 20)); homePanel.add(headerLabel);
		 */

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(); // panelen allt på main-sidan ligger på
		panel.setLayout(new GridLayout(4, 4));

		Font titleFont = new Font("Dialog", Font.PLAIN, 35); // Font för
		// rubriker

		/*
		 * Registrera: - Titel "Registrera:" - Knappar för registrering
		 */

		JLabel registerLabel = new JLabel("Registrera");
		registerLabel.setFont(titleFont);

		// - Knapparna
		JPanel regButtonPanel = new JPanel(); // panel för knappar under
		// "Registrera:"
		regButtonPanel.setLayout(new GridLayout(1, 3)); // 1 rad, 3 knappar

		JButton regStartTime = new StartTimeButton(model);
		JButton regEndTime = new FinishTimeButton(model);

		regButtonPanel.add(regStartTime);
		regButtonPanel.add(regEndTime);

		/*
		 * Resultat: - Titel "Resultat:" - Knapp(ar) för resultat
		 */
		JLabel editLabel = new JLabel("Resultat");
		editLabel.setFont(titleFont);

		// - Knapparna
		JPanel resultPanel = new JPanel(); // panel för knappar under
		// "Se/Ändra Resultat:"
		resultPanel.setLayout(new GridLayout(1, 1)); // 1 rad, 1 knapp

		JButton generateButton = new GenerateButton(model, sorter);
		resultPanel.add(generateButton);

		panel.add(registerLabel);
		panel.add(regButtonPanel);
		panel.add(editLabel);
		panel.add(resultPanel);

		// this.add(headerPanel, BorderLayout.NORTH); Läggs till om Back-knapp
		// ska användas, se kommentar ovan
		this.add(panel, BorderLayout.SOUTH);

		this.pack();
		this.setVisible(true);
	}

	// ----------------------------------- Button Classes:
	// --------------------------------------------

	/**
	 * The register Start Time button at the start page mainView.
	 */
	private class StartTimeButton extends AbstractMainViewButton {

		/* The model to use. */
		private Model<? extends AbstractDriver> model;

		/**
		 * Instantiate the start time button.
		 * 
		 * @param model
		 *            The model to use.
		 */
		public StartTimeButton(Model<? extends AbstractDriver> model) {
			super("Starttid");
			this.model = model;
		}

		/**
		 * Show the register start time view.
		 * 
		 * @param e
		 *            The action event.
		 */
		public void actionPerformed(ActionEvent e) {
			new RegisterStartTimeView(model, driverFactory);
		}

	}

	/**
	 * The register Finish Time button at the mainView.
	 */
	private class FinishTimeButton extends AbstractMainViewButton {
		/* The model to use. */
		private Model<? extends AbstractDriver> model;

		/**
		 * Instantiate the finish button.
		 * 
		 * @param model
		 *            The model to use.
		 */
		public FinishTimeButton(Model<? extends AbstractDriver> model) {
			super("Måltid");
			this.model = model;
		}

		/**
		 * Launch the register finish time view on click.
		 * 
		 * @param event
		 *            The action event.
		 */
		public void actionPerformed(ActionEvent event) {
			new RegisterFinishTimeView(model, driverFactory);
		}

	}

	/**
	 * The "generate results" button at the mainView.,
	 */
	private class GenerateButton extends AbstractMainViewButton {
		/* The generator to use. */
		private AbstractGenerator generator;
		private Model<? extends AbstractDriver> model;

		/**
		 * Instantiate the generate button.
		 */
		public GenerateButton(Model<? extends AbstractDriver> model,
				Sorter<? extends AbstractDriver> sorter) {

			super("Generera Resultat");
			this.model = model;
			generator = Configuration.generator;
		}

		/**
		 * Generate results when clicked.
		 * 
		 * @param event
		 *            The action event.
		 */
		public void actionPerformed(ActionEvent event) {

//			Model<? extends AbstractDriver> modelAll = Configuration.model;

			// find all files and parse them
			File startdir = new File("data/startFiles/");
			FilenameFilter filter = new TXTFilter();

			for (String fName : startdir.list(filter)) {
				try {
					StartParser sp = new StartParser(
							"data/startFiles/" + fName, driverFactory);
					sp.parse(model);
				} catch (IOException e) {
					error(e, "Can't parse file: " + fName);
				}
			}

			File finishdir = new File("data/finishFiles/");
			for (String fName : finishdir.list(filter)) {
				try {
					FinishParser fp = new FinishParser("data/finishFiles/"
							+ fName, driverFactory);
					fp.parse(model);
				} catch (IOException e) {
					error(e, "Can't parse file: " + fName);
				}
			}

			generator.generateResult(model);
			// TODO: Fixa så testerna går igenom utan att man behöver klicka OK
			// innan den här raden avkommenteras.
			// JOptionPane.showMessageDialog(null,
			// "The file \"data/result.txt\" has been generated!");
		}

		private class TXTFilter implements FilenameFilter {
			public boolean accept(File dir, String name) {
				if (name.equals("start-" + Configuration.stationID+ ".txt")) {
					return false;
				} else if (name.equals("finish-" + Configuration.stationID + ".txt")) {
					return false;
				} else {
					return (name.endsWith(".txt"));
				}
			}
		}

	}

}
