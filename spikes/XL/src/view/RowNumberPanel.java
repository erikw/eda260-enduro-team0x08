package view;

import java.awt.Color;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RowNumberPanel extends JPanel {
    public RowNumberPanel(int rows) {
        setLayout(new GridLayout(rows + 1, 1, 2, 2));
        JLabel label = new JLabel();
        label.setBackground(Color.LIGHT_GRAY);
        label.setOpaque(true);
        add(label);
        for (int i = 0; i < rows; i++) {
            label = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            label.setBackground(Color.LIGHT_GRAY);
            label.setOpaque(true);
            add(label);
        }
        setBackground(Color.BLACK);
    }
}