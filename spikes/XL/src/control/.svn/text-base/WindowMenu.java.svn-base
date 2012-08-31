package control;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JMenu;
import view.Gui;
import view.GuiList;

public class WindowMenu extends JMenu implements Observer {
	private GuiList guiList;

    public WindowMenu(GuiList guiList) {
        super("Window");
        this.guiList = guiList;
        guiList.addObserver(this);
        update(null, null);
    }

    public void update(Observable observable, Object object) {
        removeAll();
        for (Gui gui : guiList) {
            add(new WindowMenuItem(gui));
        }
    }
}