package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Health;

class HealthTest {

	@Test
	public void healthTest() {
		Health health = new Health("Potion of Healing",
				"A potion that restores health.", 10, 50);

		assertEquals("Potion of Healing", health.getName());
		assertEquals("A potion that restores health.", health.getDescription());
		assertEquals(10, health.getQuantity());
		assertEquals(50, health.getCost());
	}
}