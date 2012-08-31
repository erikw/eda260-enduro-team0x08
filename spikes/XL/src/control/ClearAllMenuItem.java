package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sheet.Sheet;
import view.SheetView;

public class ClearAllMenuItem extends JMenuItem implements ActionListener {
	private Sheet sheet;
	private SheetView sheetView;
	private Editor editor;
	
    public ClearAllMenuItem(Sheet sheet, SheetView sheetView, Editor editor) {
        super("Clear all");
        this.sheet = sheet;
        this.sheetView = sheetView;
        this.editor = editor;
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        sheet.clear();
        sheetView.draw();
        editor.clear();
    }
}
