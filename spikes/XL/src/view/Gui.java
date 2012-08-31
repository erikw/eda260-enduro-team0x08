package view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFrame;

import au.com.bytecode.opencsv.CSVReader;

import sheet.Sheet;
import xl.Status;

import control.Editor;
import control.ExitListener;
import control.GuiCloser;
import control.XLMenuBar;


public class Gui extends JFrame implements Printable {
	private static final int ROWS = 10, COLUMNS = 8;
	private GuiList guiList;
	private int count;

	private Gui(GuiList guiList, int count) {
		super("Untitled-" + count);
		this.guiList = guiList;
		this.count = count;
		setLocation(count * 20, count * 20);
		guiList.add(this);
		setLayout(new BorderLayout());
		try {
			CSVReader reader = new CSVReader(new FileReader("test/test.xl"), ';');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		StatusArea statusArea = new StatusArea();
		Current current = new Current();
		Sheet sheet = new Sheet();
		Status status = new Status();
		status.addObserver(statusArea);

		// statusPanel
		BorderPanel statusPanel = new BorderPanel();
		CurrentView currentView = new CurrentView();

		statusPanel.add(WEST, currentView);
		statusPanel.add(CENTER, statusArea);
		
		// sheetPanel
		BorderPanel sheetPanel = new BorderPanel();
		SheetView sheetView = new SheetView(ROWS, COLUMNS, current, sheet);
		sheetPanel.add(WEST, new RowNumberPanel(ROWS));
		sheetPanel.add(CENTER, sheetView);
		
		// gui
		Editor editor = new Editor(status, sheet, current, sheetView);
		current.addObserver(currentView);
		current.addObserver(editor);
		add(NORTH, statusPanel);
		add(CENTER, editor);
		add(SOUTH, sheetPanel);
		GuiCloser guiCloser = new GuiCloser(this, guiList);
		setJMenuBar(new XLMenuBar(this, guiList, guiCloser, status, sheetView, sheet, current, editor));
		addWindowListener(new ExitListener(guiCloser));
		pack();
		setResizable(false);
		setVisible(true);
	}

	public Gui(Gui oldGui) {
		this(oldGui.guiList, oldGui.count + 1);
	}

	public void rename(String title) {
		setTitle(title);
		guiList.setChanged();
	}

	public static void main(String[] args) {
		new Gui(new GuiList(), 0);
	}

	public int print(Graphics g, PageFormat pageFormat, int page) {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        printAll(g2d);
        return PAGE_EXISTS;
    }
}