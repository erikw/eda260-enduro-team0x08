package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
/**
 * A text field with <Enter>-listening capabilities for entering data.
 */
@SuppressWarnings("serial")
public class RegisterStartField extends JTextField implements KeyListener {
	
	/* The parent view. */
	private RegisterViewTemplate parent;
	
	/**
	 * Instantiate a register text field.
	 * @param parent The parent view.
	 */
	public RegisterStartField(RegisterViewTemplate parent) {
		super(4);
		this.parent = parent;
		this.addKeyListener(this);
		this.setName("nrField"); //for testing
	}
	
	public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    /**
     * Enter field when the enter key is pressed.
     * @param e The key event.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
        	parent.enterField();
        }
    }
}
