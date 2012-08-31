package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sheet.Sheet;
import sheet.Slot;
import view.Current;
import view.SheetView;
import xl.Status;
import xl.XLException;

public class ClearMenuItem extends JMenuItem implements ActionListener {
	private Sheet sheet;
	private SheetView sheetView;
	private Current current;
	private Status status;
	private Editor editor;

	public ClearMenuItem(Sheet sheet, SheetView sheetView, Current current, Status status, Editor editor) {
		super("Clear");
		this.sheet = sheet;
		this.sheetView = sheetView;
		this.current = current;
		this.status = status;
		this.editor = editor;
		addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent event) {
		Slot previous = sheet.get(current.getAddress());
		sheet.remove(current.getAddress());
		try {
			sheetView.draw();
		} catch (XLException xle) {
			status.setStatus(xle.getMessage());
			sheet.put(current.getAddress(), previous);
			sheetView.draw();
		}
		editor.clear();
	}
}