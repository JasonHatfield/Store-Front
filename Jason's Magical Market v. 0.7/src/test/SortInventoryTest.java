package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import app.SortInventory;
import inventory.Health;
import inventory.SalableProduct;

class SortInventoryTest {

	@Test
	void testSort() {
		String item1 = "apple";
		String item2 = "banana";
		String item3 = "orange";
		String description = "Delicious fruit";
		
		
		List<SalableProduct> inventory = new ArrayList<>();
		inventory.add(new Health(item2, description, 10, 2));
		inventory.add(new Health(item1, description, 5, 3));
		inventory.add(new Health(item3, description, 20, 1));

		// sort by name A -> Z
		SortInventory.sort(inventory, 1);
		List<String> expected1 = Arrays.asList(item1, item2, item3);
		assertEquals(expected1, inventory.stream().map(SalableProduct::getName)
				.collect(Collectors.toList()));

		// sort by name Z -> A
		SortInventory.sort(inventory, 2);
		List<String> expected2 = Arrays.asList(item3, item2, item1);
		assertEquals(expected2, inventory.stream().map(SalableProduct::getName)
				.collect(Collectors.toList()));

		// sort by price low -> high
		SortInventory.sort(inventory, 3);
		List<String> expected3 = Arrays.asList(item3, item2, item1);
		assertEquals(expected3, inventory.stream().map(SalableProduct::getName)
				.collect(Collectors.toList()));

		// sort by price high -> low
		SortInventory.sort(inventory, 4);
		List<String> expected4 = Arrays.asList(item1, item2, item3);
		assertEquals(expected4, inventory.stream().map(SalableProduct::getName)
				.collect(Collectors.toList()));

	}
}