package control;

import io.XLBufferedReader;

import java.awt.FileDialog;
import java.io.FileNotFoundException;

import javax.swing.KeyStroke;

import sheet.Sheet;
import view.Gui;
import view.SheetView;
import xl.Status;
import xl.XLException;

public class OpenMenuItem extends FileMenuItem {
	private SheetView sheetView;

	public OpenMenuItem(SheetView sheetView, Gui gui, Status status, Sheet sheet) {
		super("Open", gui, status, sheet);
		this.sheetView = sheetView;
		setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		action = FileDialog.LOAD;
	}

	public void action(String name) {
		try {
			XLBufferedReader reader = new XLBufferedReader(name);
			sheet.clear();
			reader.load(sheet);
			status.setStatus(name + " opened");

		} catch (FileNotFoundException e) {
			status.setStatus(e.getMessage());
		} catch (XLException xle) {
			status.setStatus(xle.getMessage());
			sheet.clear();
		} finally {
			sheetView.draw();
		}

	}
}