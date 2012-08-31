package common;

import javax.swing.JOptionPane;

/**
 * Static functions that are needed all over the project.
 */
public class ErrorMessage {

	/**
	 * Make a dialog with error information.
	 * 
	 * 
	 * @param exception
	 *            The exception caught.
	 * 
	 * @param comment
	 *            Additional information about the problem.
	 * @throws DebugException An runtime exception tha tells you about the real exception.
	 */
	public static void error(Exception exception, String comment) {
		StackTraceElement[] stackTraces = Thread.currentThread()
				.getStackTrace();
		// 2 calls since the caller (line above + call of this method).
		String callerClass = stackTraces[2].getClassName();
		String callerMethod = stackTraces[2].getMethodName();
		int lineNbr = stackTraces[2].getLineNumber();

		String dialogString = "Class: " + callerClass + "\nMethod: "
				+ callerMethod + "\nLine: " + lineNbr + "\nException: "
				+ exception.getClass().getName() + "\nComment: " + comment;
		if (Configuration.debug == 0) {
			new FramedErrorMessage(dialogString,"Caught an Exception!");
//		JOptionPane.showMessageDialog(null, dialogString,
//				"Caught an Exception!", JOptionPane.ERROR_MESSAGE);
		} else {
			throw new DebugException(exception, callerClass, callerMethod, lineNbr, comment);
		}
	}
}
