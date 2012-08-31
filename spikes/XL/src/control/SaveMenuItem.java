package control;

import io.XLPrintStream;
import java.awt.FileDialog;

import javax.swing.KeyStroke;

import sheet.Sheet;
import view.Gui;
import xl.Status;

public class SaveMenuItem extends FileMenuItem {
    public SaveMenuItem(Gui gui, Status status, Sheet sheet) {
        super("Save", gui, status, sheet);
        action = FileDialog.SAVE;
		setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
    }

    public void action(String name) {
        try {
            XLPrintStream printStream = new XLPrintStream(name);
            printStream.save(sheet.entrySet());
            status.setStatus(name + " saved");
        } catch (Exception e) {
        	status.setStatus(e.getMessage());
        }
    }
}