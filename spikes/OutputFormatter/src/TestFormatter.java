import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFormatter {
	private Formatter csvFormatter;
	private Formatter htmlFormatter;

	@Before
	public void setUp() {
		csvFormatter = new CSVFormatter();
		htmlFormatter = new HTMLFormatter();
	}

	@After
	public void tearDown() {
		csvFormatter = null;
		htmlFormatter = null;
	}

	@Test
	public void testCSVFileExtension() {
		assertEquals("txt", csvFormatter.getFileExtension());
	}

	@Test
	public void testHTMLFileExtension() {
		assertEquals("html", htmlFormatter.getFileExtension());
	}

	@Test
	public void testSimpleCSV() {
		String expectedCSVSimple = "StartNbr, Name, Start, Finnish\n";
		expectedCSVSimple += "1, Per Holm, 13:00:00, 13:37:00\n";
		assertEquals(expectedCSVSimple, getSimpleFormatted(csvFormatter));
	}

	@Test
	public void testSimpleHTML() {
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title></head>")
				.append("</head>").append("<body>").append("<table>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>Start</th>").append("<th>Finnish</th>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>1</td>").append("<td>Per Holm</td>")
				.append("<td>13:00:00</td>").append("<td>13:37:00</td>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("</table>").append("</body>").append("</html>");

		assertEquals(expectedBuilder.toString(),
				getSimpleFormatted(htmlFormatter));
	}

	@Test
	public void testComplexCSV() {

		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("Elite\n");
		expectedBuilder.append("StartNbr, Name, Start, Finnish\n");
		expectedBuilder.append("1, Per Holm, 13:00:00, 13:37:00\n");
		expectedBuilder.append("2, Lars Bendix, 13:05:00, 14:00:01\n");
		expectedBuilder.append("Junior\n");
		expectedBuilder
				.append("StartNbr, Name, #laps, lap1start, lap2start, lap1finnish, lap2finnish\n");
		expectedBuilder
				.append("3, Lennart Andersson, 2, 14:30:21, 15:30:22, , 16:00:00\n");
		expectedBuilder
				.append("4, Lennart Olsson, 1, 14:31:22, , 14:50:00, \n");

		assertEquals(expectedBuilder.toString(),
				getComplexFormatted(csvFormatter));
	}

	@Test
	public void testComplexHTML() {
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title></head>")
				.append("</head>").append("<body>").append("<table>");

		expectedBuilder.append("<tr><th>").append("Elite").append("</th></tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>Start</th>").append("<th>Finnish</th>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>1</td>").append("<td>Per Holm</td>")
				.append("<td>13:00:00</td>").append("<td>13:37:00</td>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>2</td>").append("<td>Lars Bendix</td>")
				.append("<td>13:05:00</td>").append("<td>14:00:01</td>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("<tr><th>").append("Junior")
				.append("</th></tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>#laps</th>");
		expectedBuilder.append("<th>lap1start</th>").append(
				"<th>lap2start</th>");
		expectedBuilder.append("<th>lap1finnish</th>").append(
				"<th>lap2finnish</th>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>3</td>")
				.append("<td>Lennart Andersson</td>").append("<td>2</td>");
		expectedBuilder.append("<td>14:30:21</td>").append("<td>15:30:22</td>")
				.append("<td></td>").append("<td>16:00:00</td>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>4</td>").append("<td>Lennart Olsson</td>")
				.append("<td>1</td>");
		expectedBuilder.append("<td>14:31:22</td>").append("<td></td>")
				.append("<td>14:50:00</td>").append("<td></td>");
		expectedBuilder.append("</tr>");

		expectedBuilder.append("</table>").append("</body>").append("</html>");
		assertEquals(expectedBuilder.toString(),
				getComplexFormatted(htmlFormatter));
	}

	private String getSimpleFormatted(Formatter formatter) {
		formatter.beginHeader();
		formatter.putHeaderValue("StartNbr").putHeaderValue("Name");
		formatter.putHeaderValue("Start").putHeaderValue("Finnish");
		formatter.endHeader();
		formatter.beginEntry();
		formatter.putEntryValue("1").putEntryValue("Per Holm");
		formatter.putEntryValue("13:00:00").putEntryValue("13:37:00");
		formatter.endEntry();

		return formatter.toString();
	}

	private String getComplexFormatted(Formatter formatter) {
		formatter.putClassHeader("Elite");
		formatter.beginHeader();
		formatter.putHeaderValue("StartNbr").putHeaderValue("Name");
		formatter.putHeaderValue("Start").putHeaderValue("Finnish");
		formatter.endHeader();

		formatter.beginEntry();
		formatter.putEntryValue(1).putEntryValue("Per Holm");
		formatter.putEntryValue("13:00:00").putEntryValue("13:37:00");
		formatter.endEntry();

		formatter.beginEntry();
		formatter.putEntryValue(2).putEntryValue("Lars Bendix");
		formatter.putEntryValue("13:05:00").putEntryValue("14:00:01");
		formatter.endHeader();

		formatter.putClassHeader("Junior");
		formatter.beginHeader();
		formatter.putHeaderValue("StartNbr").putHeaderValue("Name");
		formatter.putHeaderValue("#laps").putHeaderValue("lap1start");
		formatter.putHeaderValue("lap2start").putHeaderValue("lap1finnish");
		formatter.putHeaderValue("lap2finnish");
		formatter.endHeader();

		formatter.beginEntry();
		formatter.putEntryValue(3).putEntryValue("Lennart Andersson");
		formatter.putEntryValue(2).putEntryValue("14:30:21");
		formatter.putEntryValue("15:30:22");
		formatter.fillEmptyEntries(1);
		formatter.putEntryValue("16:00:00");
		formatter.endEntry();

		formatter.beginEntry();
		formatter.putEntryValue(4).putEntryValue("Lennart Olsson");
		formatter.putEntryValue(1).putEntryValue("14:31:22");
		formatter.fillEmptyEntries(1);
		formatter.putEntryValue("14:50:00");
		formatter.fillEmptyEntries(1);
		formatter.endEntry();

		return formatter.toString();
	}

}
