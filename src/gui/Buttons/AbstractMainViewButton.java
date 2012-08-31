package gui.Buttons;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * A Button template for the BIG buttons at the mainView.
 */
@SuppressWarnings("serial")
public abstract class AbstractMainViewButton extends JButton implements
		ActionListener {

	/**
	 * Instantiate the button.
	 * 
	 * @param name
	 *            The label-name of the button.
	 */
	public AbstractMainViewButton(String name) {
		super(name);
		this.addActionListener(this);
		this.setFont(new Font("Monospaced", Font.BOLD, 40));
	}

	/**
	 * What action to be taken on click.
	 * 
	 * @param event
	 *            The action event.
	 */
	public abstract void actionPerformed(ActionEvent event);

}
