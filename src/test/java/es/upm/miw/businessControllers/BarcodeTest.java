package es.upm.miw.businessControllers;

import static org.junit.Assert.*;

import org.junit.Test;

import es.upm.miw.businessServices.Barcode;

public class BarcodeTest {

    @Test
    public void testGenerateEan13code() {
        assertEquals(13, new Barcode().generateEan13code(1).length());
        assertEquals(13, new Barcode().generateEan13code(123456789012L).length());
    }

}
