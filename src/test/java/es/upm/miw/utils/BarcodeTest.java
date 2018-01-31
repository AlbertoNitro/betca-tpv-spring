package es.upm.miw.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class BarcodeTest {

    @Test
    public void testGenerateEan13code() {
        assertEquals(13, new Barcode().generateEan13code(1).length());
        assertEquals(13, new Barcode().generateEan13code(123456789012L).length());
    }

}
