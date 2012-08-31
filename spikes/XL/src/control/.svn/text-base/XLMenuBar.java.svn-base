package control;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import sheet.Sheet;
import view.Current;
import view.Gui;
import view.GuiList;
import view.SheetView;
import xl.Status;

public class XLMenuBar extends JMenuBar {

	public XLMenuBar(Gui gui, GuiList guiList, GuiCloser guiCloser,
			Status status, SheetView sheetView, Sheet sheet, Current current,
			Editor editor) {
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		file.add(new PrintMenuItem(gui, status));
		file.add(new SaveMenuItem(gui, status, sheet));
		file.add(new OpenMenuItem(sheetView, gui, status, sheet));
		file.add(new NewMenuItem(gui));
		file.add(new CloseMenuItem(guiCloser));
		edit.add(new ClearMenuItem(sheet, sheetView, current, status, editor));
		edit.add(new ClearAllMenuItem(sheet, sheetView, editor));
		add(file);
		add(edit);
		add(new WindowMenu(guiList));
	}
}