package test;

import org.junit.Test;

import common.Configuration;
import common.DebugException;
import common.ErrorMessage;

public class TestErrorMessage {

	
	@Test(expected = DebugException.class)
	public void testErrorMessage() {
		Configuration.debug = 1;
		ErrorMessage.error(new Exception(), "Testing error message");
	}

}
