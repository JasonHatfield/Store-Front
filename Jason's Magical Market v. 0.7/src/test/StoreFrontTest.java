package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import driver.StoreFront;

public class StoreFrontTest {
	@Test
	public void testStoreName() {
		assertEquals("~={ Jason's Magical Market }=~", StoreFront.getStoreName());
	}

	@Test
	public void testWelcomeMessage() {
		// Use assertDoesNotThrow() to test the welcomeMessage() method without
		// capturing its output.
		assertDoesNotThrow(StoreFront::welcomeMessage);
	}
}