package test;

import static org.junit.Assert.assertEquals;
import generator.formatter.CSVFormatter;
import generator.formatter.Formatter;
import generator.formatter.HTMLFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;

public class TestFormatter {
	private Formatter csvFormatter;
	private Formatter htmlFormatter;

	@Before
	public void setUp() {
		csvFormatter = new CSVFormatter();
		htmlFormatter = new HTMLFormatter();
		Configuration.debug = 1;
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
		String expectedCSVSimple = "StartNbr; Name; Start; Finish\n";
		expectedCSVSimple += "1; Per Holm; 13:00:00; 13:37:00\n";
		assertEquals(expectedCSVSimple, getSimpleFormatted(csvFormatter));
	}

	@Test
	public void testSimpleHTML() {
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title>");
		expectedBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
		expectedBuilder.append("</head>").append("<body>").append("<table>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>Start</th>").append("<th>Finish</th>");
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
		expectedBuilder.append("StartNbr; Name; Start; Finish\n");
		expectedBuilder.append("1; Per Holm; 13:00:00; 13:37:00\n");
		expectedBuilder.append("2; Lars Bendix; 13:05:00; 14:00:01\n");
		expectedBuilder.append("Junior\n");
		expectedBuilder
				.append("StartNbr; Name; #laps; lap1start; lap2start; lap1finish; lap2finish\n");
		expectedBuilder
				.append("3; Lennart Andersson; 2; 14:30:21; 15:30:22; ; 16:00:00\n");
		expectedBuilder
				.append("4; Lennart Olsson; 1; 14:31:22; ; 14:50:00; \n");

		assertEquals(expectedBuilder.toString(),
				getComplexFormatted(csvFormatter));
	}

	@Test
	public void testComplexHTML() {
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title>");
		expectedBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				expectedBuilder.append("</head>").append("<body>").append("<table>");

		expectedBuilder.append("<tr><th>").append("Elite").append("</th></tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>Start</th>").append("<th>Finish</th>");
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
		expectedBuilder.append("<th>lap1finish</th>").append(
				"<th>lap2finish</th>");
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
		formatter.putHeaderValue("Start").putHeaderValue("Finish");
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
		formatter.putHeaderValue("Start").putHeaderValue("Finish");
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
		formatter.putHeaderValue("lap2start").putHeaderValue("lap1finish");
		formatter.putHeaderValue("lap2finish");
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

	private void headerWithLaps1(Formatter formatter) {
		formatter.beginHeader();
		formatter.putHeaderValue("StartNbr").putHeaderValue("Name")
				.putHeaderValue("Lap 1").putHeaderValue("Lap 2")
				.putHeaderValue("Lap 3");
		formatter.endHeader();
	}

	private void headerWithLaps2(Formatter formatter) {
		formatter.beginHeader();
		formatter.putHeaderValue("StartNbr").putHeaderValue("Name");
		formatter.fillEmptyHeaders(1);
		formatter.endHeader();
	}

	@Test
	public void htmlFillEmptyEntries() {
		headerWithLaps1(htmlFormatter);
		htmlFormatter.beginEntry();
		htmlFormatter.putEntryValue("1").putEntryValue("Shesus")
				.fillEmptyEntries(2);
		htmlFormatter.endEntry();
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title>");
		expectedBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				expectedBuilder.append("</head>").append("<body>").append("<table>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th>Lap 1</th>").append("<th>Lap 2</th>")
				.append("<th>Lap 3</th>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>1</td>").append("<td>Shesus</td>")
				.append("<td></td>").append("<td></td>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("</table>").append("</body>").append("</html>");
		assertEquals(expectedBuilder.toString(), htmlFormatter.toString());
	}

	@Test
	public void htmlFillEmptyHeaders() {
		headerWithLaps2(htmlFormatter);
		htmlFormatter.beginEntry();
		htmlFormatter.putEntryValue("1").putEntryValue("Mubarak")
				.putEntryValue("?polylog");
		htmlFormatter.endEntry();
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title>");
		expectedBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				expectedBuilder.append("</head>").append("<body>").append("<table>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>StartNbr</th>").append("<th>Name</th>")
				.append("<th></th>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<td>1</td>").append("<td>Mubarak</td>")
				.append("<td>?polylog</td>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("</table>").append("</body>").append("</html>");
		assertEquals(expectedBuilder.toString(), htmlFormatter.toString());
	}

	@Test
	public void htmlTestHeader() {
		htmlFormatter.beginHeader();
		htmlFormatter.putHeaderValue(1);
		htmlFormatter.endHeader();
		StringBuilder expectedBuilder = new StringBuilder();
		expectedBuilder.append("<html>")
				.append("<head><title>Enduro resultat</title>");
		expectedBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
				expectedBuilder.append("</head>").append("<body>").append("<table>");
		expectedBuilder.append("<tr>");
		expectedBuilder.append("<th>1</th>");
		expectedBuilder.append("</tr>");
		expectedBuilder.append("</table>").append("</body>").append("</html>");
		assertEquals(expectedBuilder.toString(), htmlFormatter.toString());
	}

	@Test
	public void csvFillEmptyEntriesWhenNoEntries() {
		csvFormatter.beginHeader().putHeaderValue(1).putHeaderValue(2).endHeader().beginEntry()
				.fillEmptyEntries(1).endEntry();
		String expectedCSVSimple = "1; 2\n";
		expectedCSVSimple += "; \n";
		assertEquals(expectedCSVSimple, csvFormatter.toString());
	}
}
