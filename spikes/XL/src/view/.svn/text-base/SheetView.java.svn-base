package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sheet.Sheet;

import xl.Address;
import xl.Adjustment;
import xl.NumberAdjustment;

public class SheetView extends JPanel{
	private static Adjustment adjustment = new NumberAdjustment(14, 2);
	private SlotView slotViews[][];
	private int rows;
	private int cols;

	public SheetView(int rows, int cols, Current current, Sheet sheet) {
		this.rows = rows;
		this.cols = cols;
		ClickListener clickListener = new ClickListener();
		addMouseListener(clickListener);
		slotViews = new SlotView[rows][cols];
		setLayout(new GridLayout(rows + 1, cols, 2, 2));
		for (int j = 0; j < cols; j++) {
			JLabel lbl = new JLabel(String.valueOf((char) (j + 'A')),
					SwingConstants.CENTER);
			lbl.setBackground(Color.LIGHT_GRAY);
			lbl.setOpaque(true);
			add(lbl);
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				SlotView slotViewIj = new SlotView(current, new Address(i, j), sheet, adjustment);
				add(slotViewIj);
				slotViews[i][j] = slotViewIj;
				slotViewIj.addMouseListener(clickListener);
				if (i == 0 && j == 0) {
					slotViewIj.mark();
				}
			}
		}
		setBackground(Color.BLACK);
	}

	private class ClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			SlotView s = null;
			try {
				s = (SlotView) e.getComponent();
				s.mark();
			} catch (ClassCastException ex) {
			}
		}
	}

	public void draw() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				slotViews[i][j].draw();
			}
		}
	}
}
