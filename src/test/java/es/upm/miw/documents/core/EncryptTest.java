package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.upm.miw.utils.Encrypting;

public class EncryptTest {

    @Test
    public void testEncryptInBase64UrlSafe() {
        String url = new Encrypting().encryptInBase64UrlSafe();
        assertEquals(-1, url.indexOf("+"));
        assertEquals(-1, url.indexOf("/"));
        assertEquals(-1, url.indexOf("="));
    }

}
