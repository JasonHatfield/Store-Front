package test;

import static org.junit.Assert.*;
import org.junit.Test;

import inventory.SalableProduct;

public class SalableProductTest {
	String name = "name";
	String description = "description";
	int quantity = 10;
	int cost = 100;
	
	@Test
    public void testConstructor() {

    	
        SalableProduct p = new SalableProduct(name, description, quantity, cost) {
            // Anonymous class to extend SalableProduct for testing purposes
        };

        assertEquals(name, p.getName());
        assertEquals(description, p.getDescription());
        assertEquals(quantity, p.getQuantity());
        assertEquals(cost, p.getCost());
    }

    @Test
    public void testSettersAndGetters() {
        SalableProduct p = new SalableProduct() {
            // Anonymous class to extend SalableProduct for testing purposes
        };

        p.setName(name);
        p.setDescription(description);
        p.setQuantity(quantity);
        p.setCost(cost);

        assertEquals(name, p.getName());
        assertEquals(description, p.getDescription());
        assertEquals(quantity, p.getQuantity());
        assertEquals(cost, p.getCost());
    }
}