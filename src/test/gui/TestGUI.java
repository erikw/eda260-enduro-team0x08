package test.gui;

import generator.MarathonGenerator;
import gui.MainView;
import gui.RegisterStartField;
import gui.RegisterStartTimeView;
import gui.RegisterViewTemplate;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.FrameFinder;

import org.junit.After;
import org.junit.Test;

import common.Configuration;
import common.DebugException;
import common.EnduroTime;

import driver.factory.MarathonFactory;

public class TestGUI extends JFCTestCase {
	private MainView mainView = null;
	private int waitTime = 3;

	public void setUp() {
		Configuration.debug = 1;
		Configuration.factory = new MarathonFactory();
		Configuration.generator = new MarathonGenerator(Configuration.nameFile,
				Configuration.sorter);
		
		
		//For coverage
		File f = new File(Configuration.startFile);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f = new File(Configuration.finishFile);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Configuration.factory = new MarathonFactory();
		setHelper(new JFCTestHelper());
		
		mainView = new MainView();
	}

//	@Test
//	public void testCreateStartFile() throws AWTException, FileNotFoundException{
//		File f = new File("data/finishFiles/finish-01.txt");
//		PrintWriter pw = new PrintWriter(f);
//		pw.println("aa");
//		pw.flush();
//		pw.close();
//
//		AbstractButtonFinder finder = new AbstractButtonFinder(
//		"Generera Resultat");
//		finder.setWait(waitTime);
//		JButton generate = (JButton) finder.find();
//		generate.doClick();
//		assertEquals("Generera Resultat", generate.getText());
//		
//		// TODO: Dosen't work!
//		Robot robot = new Robot();
//		robot.delay(500);
//		robot.keyPress(KeyEvent.VK_ENTER);
//		robot.keyRelease(KeyEvent.VK_ENTER);
//		
//		f.delete();
//	}
//	
	@Test
	public void testMainFrameIsShows() {
		FrameFinder df = new FrameFinder("Home");
		df.setWait(waitTime);
		assertEquals(mainView.getTitle(), ((JFrame) df.find()).getTitle());
	}

	@Test
	public void testPressGenerateResult() throws AWTException {
		AbstractButtonFinder finder = new AbstractButtonFinder(
				"Generera Resultat");
		finder.setWait(waitTime);
		JButton generate = (JButton) finder.find();
		generate.doClick();
		assertEquals("Generera Resultat", generate.getText());

		// TODO: Dosen't work!
		Robot robot = new Robot();
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	@Test
	public void testPressStartButtonAndStartFrame() {
		AbstractButtonFinder finder = new AbstractButtonFinder("Starttid");
		finder.setWait(waitTime);
		JButton start = (JButton) finder.find();
		start.doClick();

		FrameFinder df = new FrameFinder("Registrerar Starttid");
		df.setWait(waitTime);
		assertTrue(df.findAll().size() > 0);
		assertTrue(df.find(0) instanceof JFrame);
		assertEquals("Registrerar Starttid", ((JFrame) df.find()).getTitle());
	}

	@Test
	public void testPressFinishButtonAndFinishFrame() {
		AbstractButtonFinder finder = new AbstractButtonFinder("Måltid");
		finder.setWait(waitTime);
		JButton start = (JButton) finder.find();
		start.doClick();

		FrameFinder df = new FrameFinder("Registrerar Sluttid");
		df.setWait(waitTime);
		assertTrue(df.findAll().size() > 0);
		assertTrue(df.find(0) instanceof JFrame);
	}

	@Test
	public void testRegisterStart() {
		testStartOrFinish("Starttid");
	}

	@Test
	public void testRegisterStartAll() {
		testStartOrFinish("Starttid");
		AbstractButtonFinder finder = new AbstractButtonFinder("Starta alla!");
		finder.setWait(waitTime);
		JButton startalla;
		assertNotNull(startalla = (JButton) finder.find());
		startalla.doClick();

		ComponentFinder cf = new ComponentFinder(JTextField.class);
		cf.setWait(waitTime);
		assertNotNull(cf.find());
		JTextField cfField = (JTextField) cf.find(1);
		cfField.setText("1-2");
		finder = new AbstractButtonFinder("Ok");
		startalla = (JButton) finder.find();
		startalla.doClick();

	}

	@Test
	public void testRegisterFinish() {
		testStartOrFinish("Måltid");
	}

	private void testStartOrFinish(String buttonName) {
		// kollar om knappen starttid finns och klickar på den
		AbstractButtonFinder finder = new AbstractButtonFinder(buttonName);
		finder.setWait(waitTime);

		JButton start;
		assertNotNull(start = (JButton) finder.find());
		start.doClick();

		// koller om det finns ett JTextField i registrera rutan
		// och sätter in en 1a i den.
		ComponentFinder cf = new ComponentFinder(JTextField.class);
		cf.setWait(waitTime);

		assertNotNull(cf.find());
		JTextField cfField = (JTextField) cf.find(0);
		assertTrue(cfField instanceof RegisterStartField);
		RegisterStartField rsf = (RegisterStartField) cfField;
		rsf.setText("1");

		// letar efter "Registrera" knappen och trycker på den
		finder.setText("Registrera");
		JButton registrera;
		assertNotNull(registrera = ((JButton) finder.find()));
		registrera.doClick();
		// This ought to solve the sometimes working test
		EnduroTime comparisonTime = new EnduroTime();

		assertNotNull(cf.find());
		JTextField cfField2 = (JTextField) cf.find(0);
		assertTrue(cfField2 instanceof RegisterStartField);
		rsf.setText("");

		// letar efter "Registrera" knappen och trycker på den
		finder.setText("Registrera");
		assertNotNull(registrera = ((JButton) finder.find()));
		// Robot robot;
		// try {
		// robot = new Robot();
		// robot.keyPress(KeyEvent.VK_ENTER);
		// robot.keyRelease(KeyEvent.VK_ENTER);
		// } catch (AWTException e) {
		//
		// }

		// kollar om alla kolumner i raden stämmer överens
		cf.setComponentClass(JTable.class);
		JTable results;
		assertNotNull(results = (JTable) cf.find());

		assertEquals(1, results.getModel().getValueAt(0, 0));
		// Testet fungerar ibland, det beror på vad klockan är när testet
		// körs....
		//Tobias: Eins! Ich hade der kod geändert ein bisschen sagte der Hilbert.
		//Tydligen ligger en extra gömd kolumn med startnumret kvar, jag tror att vi redan visste detta.
		
		assertEquals(comparisonTime.toString(), results.getModel().getValueAt(0, 2));
		assertEquals("XXXX", results.getModel().getValueAt(0, 3));
	}

	@Test
	public void testInvalidnumber() {
		Configuration.debug = 0;
		AbstractButtonFinder finder = new AbstractButtonFinder("Starttid");

		finder.setWait(waitTime);
		JButton start = (JButton) finder.find();
		start.doClick();
		ComponentFinder cf = new ComponentFinder(JTextField.class);
		cf.setWait(waitTime);

		assertNotNull(cf.find());
		JTextField cfField = (JTextField) cf.find(0);
		assertTrue(cfField instanceof RegisterStartField);
		RegisterStartField rsf = (RegisterStartField) cfField;
		rsf.setText("a");
		finder.setText("Registrera");
		JButton registrera;
		assertNotNull(registrera = ((JButton) finder.find()));
		registrera.doClick();
		
		//ErrorMessage handling, this code is majorly general
		FrameFinder erfcf = new FrameFinder("Caught an Exception!");		
		erfcf.setWait(waitTime);
		JFrame fr;
		assertNotNull(fr = (JFrame) erfcf.find());
		assertEquals("Caught an Exception!",fr.getTitle());
		AbstractButtonFinder buttonFinder = new AbstractButtonFinder("OK");

		//Click away the bloody errorMessage
		JButton ok;
		buttonFinder.setWait(waitTime);
		assertNotNull(ok  = (JButton) buttonFinder.find());
		ok.doClick();

		// Robot robot;
		// finder.setWait(waitTime);
		//
		// try {
		// robot = new Robot();
		// robot.keyPress(KeyEvent.VK_ENTER);
		// robot.keyRelease(KeyEvent.VK_ENTER);
		// } catch (AWTException e) {
		// }

	}
	
	@Test
	public void testKeyEvent(){
		RegisterStartField rsf = new RegisterStartField(new RegisterStartTimeView(Configuration.model, Configuration.factory));
		rsf.keyPressed(new KeyEvent(rsf, 0, 0, 0, KeyEvent.VK_ENTER,' ',0));
		rsf.keyTyped(null);
		rsf.keyReleased(null);
		assertTrue(true);
	}
	


	@After
	public void tearDown() throws Exception {
		mainView.dispose();
		mainView = null;
		getHelper();
		TestHelper.cleanUp(this);
		super.tearDown();
		Configuration.debug = 1;
		new File(Configuration.startFile).delete();
		new File(Configuration.finishFile).delete();
	}

}
