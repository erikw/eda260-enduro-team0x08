package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import sheet.Sheet;
import sheet.Slot;
import view.Current;
import view.SheetView;
import xl.Address;
import xl.CyclicReferenceException;
import xl.EmptyInputException;
import xl.Status;
import xl.XLException;

public class Editor extends JTextField implements ActionListener, Observer {
	private Status status;
	private Sheet sheet;
	private Current current;
	private SheetView sheetView;

	public Editor(Status status, Sheet sheet, Current current, SheetView sheetView) {
		this.status = status;
		this.sheet = sheet;
		this.current = current;
		this.sheetView = sheetView;
		setBackground(Color.WHITE);
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		Address currentAddress = current.getAddress();
		String previous = sheet.toString(current.getAddress());
		try {
			sheet.put(currentAddress, getText());
		} catch (EmptyInputException ex) {
			sheet.remove(currentAddress);
		} catch (CyclicReferenceException xre) {
			status.setStatus(xre.getMessage());
			sheet.put(current.getAddress(), previous);
		} catch (XLException xle) {
			status.setStatus(xle.getMessage());
		} finally {
			sheetView.draw();
			setText("");
		}
	}

	public void update(Observable obs, Object arg) {
		Current current = (Current) obs;
		setText(sheet.toString(current.getAddress()));
		status.clear();
	}

	public void clear() {
		setText("");
	}
}