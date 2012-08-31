package control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.Gui;
import view.GuiList;

public class ExitListener extends WindowAdapter {
	private GuiCloser guiCloser;

	public ExitListener(GuiCloser guiCloser) {
		this.guiCloser = guiCloser;
	}
    public void windowClosing(WindowEvent event) {
    	guiCloser.close();
    }
}