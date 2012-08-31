package test.gui;


import static org.junit.Assert.*;

import org.junit.Test;


import common.FramedErrorMessage;

public class TestFramedErrorMessage {
	
	@Test
	public void testFramedErrorMsg(){
		FramedErrorMessage frame = new FramedErrorMessage("Random!", "Caught an Exception");
		assertTrue(frame.getTitle() == "Caught an Exception");
		frame.actionPerformed(null);
		assertFalse(frame.isVisible());
	}

}
