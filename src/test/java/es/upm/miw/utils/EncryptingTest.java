package es.upm.miw.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EncryptingTest {

    @Test
    public void testEncryptInBase64UrlSafe() {
        assertNotEquals(new Encrypting().encryptInBase64UrlSafe(), new Encrypting().encryptInBase64UrlSafe());
        assertTrue(new Encrypting().encryptInBase64UrlSafe().matches("[\\w\\-]{20,}"));
    }

    @Test
    public void testEncodeHexInBase64UrlSafeDecode() {
       String encode = new Encrypting().encodeHexInBase64UrlSafe("ff00b83445");
       assertEquals("ff00b83445",new Encrypting().decodeBase64InHex(encode));
    }
    
    @Test
    public void testShortId64UrlSafe() {
       assertEquals(8,new Encrypting().shortId64UrlSafe().length());
    }

}
