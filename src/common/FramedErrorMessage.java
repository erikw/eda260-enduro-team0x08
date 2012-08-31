package common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FramedErrorMessage extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton okButton;
	private JLabel label;

	public FramedErrorMessage(String dialogString, String title) {
		super(title);
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		label = new JLabel(dialogString);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		label = new JLabel(toHtml(dialogString));
        getContentPane().add(label, BorderLayout.CENTER);
		getContentPane().add(okButton, BorderLayout.SOUTH);
		pack();
		setMinimumSize(new Dimension(250, 100));
		setVisible(true);
	}

	private String toHtml(String dialogString) {
		String[] str = dialogString.split("\n");
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		for(int i = 0; i < str.length -1; i++){
			sb.append(str[i] + "<br>");
		}
		sb.append(str[str.length-1]);
		sb.append("</html>");
		return sb.toString();
	}

	public void actionPerformed(ActionEvent e) {
		dispose();
	}

}
