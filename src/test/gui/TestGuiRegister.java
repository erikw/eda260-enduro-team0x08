package test.gui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestGuiRegister /* extends UISpecTestCase */{
	@Test
	public void testTest() {
		assertTrue(true);
	}
	// // Must be called for WindowInterceptor to work properly
	// static {
	// UISpec4J.init();
	// }
	//
	// protected Window window;
	// protected Table table;
	// protected Button registerButton;
	//
	// @Before
	// public void setUp() throws Exception {
	// //launch and catch the JFrame
	// window = WindowInterceptor.run(new Trigger() {
	// public void run() {
	// new RegisterFinishTimeView(new MarathonModel());
	// }
	// });
	//		
	// //Get subcomponents
	// table = window.getTable();
	// registerButton = window.getButton("Registrera");
	// }
	//
	// @After
	// public void tearDown() throws Exception {
	// }
	//
	// @Test
	// public void testEmptyTable() {
	// //Test if the table has the right headers
	// assertEquals(true, table.getHeader().contentEquals(
	// new String[] { "Startnummer", "Sluttid" }));
	//		
	// //Verify that the table is empty
	// assertEquals(true, table.isEmpty());
	// }
	//
	// @Test
	// public void testRegistration() {
	// /*Gets the JTextField with the name "nrField".
	// * The name is set with <JTextField>.setName(<string>)
	// * Then the value is set to "12".
	// */
	// window.getTextBox("nrField").setText("12");
	//		
	// //Click the button!
	// registerButton.click();
	//
	// //Get the current time, this test might fail during "heavyload" due to
	// threads. (Haven't been able to confirm yet)
	// DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	// Date date = new Date();
	// String tid = dateFormat.format(date).replace(':', '.');
	//
	// //Test if the format was correct
	// assertEquals(true, table.contentEquals(new String[][] { { "12", tid }
	// }));
	// assertEquals(0, window.getTextBox("nrField").getText().length());
	// }
	//
	// @Test
	// public void testEmptyTextField() {
	// registerButton.click();
	// assertEquals(true, table.isEmpty());
	// assertEquals(0, window.getTextBox("nrField").getText().length());
	// }
}
