package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.Gui;

public class NewMenuItem extends JMenuItem implements ActionListener {
    private Gui gui;

    public NewMenuItem(Gui gui) {
        super("New");
        this.gui = gui;
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent event) {
        new Gui(gui);
    }
}