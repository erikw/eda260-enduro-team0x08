package view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

public class CurrentView extends JLabel implements Observer {
    public CurrentView() {
        super("A1");
        setBackground(Color.WHITE);
        setOpaque(true);
    }

	public void update(Observable obs, Object arg) {
		  setText(((Current) obs).getAddress().toString());
	}
}