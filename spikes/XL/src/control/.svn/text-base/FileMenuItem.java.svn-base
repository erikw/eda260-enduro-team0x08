package control;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import sheet.Sheet;
import view.Gui;
import view.StatusArea;
import xl.Status;

public abstract class FileMenuItem extends JMenuItem implements ActionListener {
    private static final String EXTENSION = ".xl";
    protected Gui gui;
    protected int action;
    private String title;
    protected Status status;
    protected Sheet sheet;

    protected FileMenuItem(String title, Gui gui, Status status, Sheet sheet) {
        super(title);
        this.gui = gui;
        this.title = title;
        this.status = status;
        this.sheet = sheet;
        addActionListener(this);
    }

    public abstract void action(String fullName);

    public void actionPerformed(ActionEvent event) {
        FileDialog dialog = new FileDialog(gui, title, action);
        dialog.setVisible(true);
        String file = dialog.getFile();
        String dir = dialog.getDirectory();
        dialog.dispose();
        if (file == null) {
            return;
        }
        if (!file.endsWith(EXTENSION)) {
            file += EXTENSION;
        }
        status.clear();
        action(dir + file);
        String name = file.substring(0, file.lastIndexOf('.'));
        gui.rename(name);
    }
}