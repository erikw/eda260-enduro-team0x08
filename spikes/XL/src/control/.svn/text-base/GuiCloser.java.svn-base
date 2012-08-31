package control;

import view.Gui;
import view.GuiList;

public class GuiCloser {
	private Gui gui;
	private GuiList guiList;

	public GuiCloser(Gui gui, GuiList guiList) {
		this.gui = gui;
		this.guiList = guiList;
	}

	public void close() {
		guiList.remove(gui);
		gui.dispose();
		if (guiList.isEmpty()) {
			System.exit(0);
		} else {
			guiList.last().toFront();
		}
	}
}
