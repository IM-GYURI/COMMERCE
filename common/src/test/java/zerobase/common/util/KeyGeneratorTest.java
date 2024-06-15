package zerobase.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class KeyGeneratorTest {

  @Test
  public void testGenerateKey() {
    String key = KeyGenerator.generateKey();

    assertNotNull(key);
    assertEquals(32, key.length());
    assertTrue(key.matches("[a-f0-9]+"));
  }
}