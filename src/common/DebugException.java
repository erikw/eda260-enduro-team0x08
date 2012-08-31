package common;
/**
 * An debug exception that can be thrown at runtime. It tells about a real exception.
 */
@SuppressWarnings("serial")
public class DebugException extends RuntimeException {

		public DebugException(Exception realException, String className, String methodName, int lineNbr, String comment) {
			super("Real exception was: " + realException.getClass() + ".\nClass: " + className + ".\nMethod: " + methodName + ".\nLine: " + lineNbr +".\nComment: " + comment);
		}
}
