package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.Gui;
import xl.Status;


public class PrintMenuItem extends JMenuItem implements ActionListener {
    private Gui gui;
    private Status status;
    
    public PrintMenuItem(Gui gui, Status status) {
        super("Print");
        this.gui = gui;
        this.status = status;
        addActionListener(this);
        setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
                java.awt.Event.CTRL_MASK));
    }


    public void actionPerformed(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(gui);
        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
            	status.setStatus(e.getMessage());
            }
        }
    }
}