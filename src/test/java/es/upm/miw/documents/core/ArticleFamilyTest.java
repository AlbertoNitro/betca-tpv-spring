package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArticleFamilyTest {

    @Test
    public void CratArtileFamily() {
        ArticleFamily articleFamily = new ArticleFamily("111", "000", "Family Raiz");
        assertEquals("ArticleFamily [idhijo=111, idpadre=000, reference=Family Raiz]", articleFamily.toString());

    }

}
