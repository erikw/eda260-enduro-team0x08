package view;

import java.awt.Color;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import sheet.Sheet;

import xl.Address;
import xl.Adjustment;

public class SlotView extends JLabel implements Observer {
	private Current current;
	private Address address;
	private Sheet sheet;
	private Adjustment adjustment;

	public SlotView(Current current, Address address, Sheet sheet,
			Adjustment adjustment) {
		super("                    ", RIGHT);
		this.current = current;
		this.address = address;
		this.sheet = sheet;
		this.adjustment = adjustment;
		setBackground(Color.WHITE);
		setHorizontalAlignment(RIGHT);
		setOpaque(true);
	}

	public void update(Observable obs, Object arg) {
		setBackground(Color.WHITE);
		obs.deleteObserver(this);
	}

	public void mark() {
		current.set(address);
		setBackground(Color.YELLOW);
		current.addObserver(this);
	}

	public void draw() {
		setText(sheet.toString(address, adjustment));
	}
}