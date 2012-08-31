package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.Gui;
import view.GuiList;

public class CloseMenuItem extends JMenuItem implements ActionListener {
	private GuiCloser guiCloser;

	public CloseMenuItem(GuiCloser guiCloser) {
		super("Close");
		addActionListener(this);
		this.guiCloser = guiCloser;
		setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
                java.awt.Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent event) {
		guiCloser.close();
	}
}
