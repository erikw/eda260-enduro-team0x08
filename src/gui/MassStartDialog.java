package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Model;

import common.EnduroTime;

import driver.AbstractDriver;

public class MassStartDialog extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField textfield;
	private JButton button = new JButton("Ok");
	private JPanel panel = new JPanel();
	private Model<? extends AbstractDriver> model;

	public MassStartDialog(Model<? extends AbstractDriver> model) {
		super("Masstart");
		setResizable(false);
		textfield = new JTextField(10);
		textfield.setFont(new Font("Monospaced", Font.BOLD, 40));
		this.model = model;
		button.setFont(new Font("Monospaced", Font.BOLD, 40));
		button.addActionListener(this);
		panel.add(textfield);
		panel.add(button);
		this.add(panel);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String toBeParsed = textfield.getText();
		String[] splitted = toBeParsed.split(",");
		for (String range : splitted) {
			String[] splitRange = range.split("-");
			if (splitRange.length == 2) {
				EnduroTime et = new EnduroTime();
				model.generateMassStartWave(Integer.valueOf(splitRange[0]),
						Integer.valueOf(splitRange[1]), et.toString());
			}
		}
	}

}
