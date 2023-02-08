package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Health;

class WeaponTest {

	@Test
	public void weaponTest() {
		Health weapon = new Health("Short Sword",
				"A small sword typically weilded by hobbits.", 5, 75);

		assertEquals("Short Sword", weapon.getName());
		assertEquals("A small sword typically weilded by hobbits.", weapon.getDescription());
		assertEquals(5, weapon.getQuantity());
		assertEquals(75, weapon.getCost());
	}
}