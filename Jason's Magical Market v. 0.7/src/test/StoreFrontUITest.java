package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import driver.StoreFrontUI;

/**
 * Test the {@link StoreFrontUI} class.
 */
public class StoreFrontUITest {
  // Stream to capture output from StoreFrontUI.displayOptions()
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  // Set up the stream before each test
  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  /**
   * Test the displayOptions method of the {@link StoreFrontUI} class.
   */
  @Test
  public void testStoreFrontUI() {
    // Invoke the method under test
    StoreFrontUI.displayOptions();

    // Check that the method produces the expected output
    assertEquals("", outContent.toString());
  }
}
