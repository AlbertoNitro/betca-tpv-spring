package es.upm.miw.utils;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EncryptingTest {

    @Test
    public void testEncryptInBase64UrlSafe() {
        assertNotEquals(new Encrypting().encryptInBase64UrlSafe(),new Encrypting().encryptInBase64UrlSafe());
        assertTrue(new Encrypting().encryptInBase64UrlSafe().matches("[\\w\\-]{20,}"));
    }

}
