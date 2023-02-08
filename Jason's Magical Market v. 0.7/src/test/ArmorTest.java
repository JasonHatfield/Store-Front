package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Armor;

public class ArmorTest {
	/**
	 * Test the {@link Armor} class constructor.
	 */
	@Test
	public void testArmorConstructor() {
	  // Create a new Armor object
	  Armor armor = new Armor("Chainmail",
	      "A suit of armor made of interlocking metal rings.", 10, 100);

	  // Check that the object has the expected properties
	  assertEquals("Chainmail", armor.getName());
	  assertEquals("A suit of armor made of interlocking metal rings.",
	      armor.getDescription());
	  assertEquals(10, armor.getQuantity());
	  assertEquals(100, armor.getCost());
	}
}