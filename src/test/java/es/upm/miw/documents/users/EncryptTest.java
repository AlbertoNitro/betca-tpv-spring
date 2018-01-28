package es.upm.miw.documents.users;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import es.upm.miw.utils.Encrypting;

public class EncryptTest {

    @Test
    public void testEncryptInBase64UrlSafe() {
        String url = new Encrypting().encryptInBase64UrlSafe(Calendar.getInstance().toString());
        assertEquals(-1, url.indexOf("+"));
        assertEquals(-1, url.indexOf("/"));
        assertEquals(-1, url.indexOf("="));
    }

}
