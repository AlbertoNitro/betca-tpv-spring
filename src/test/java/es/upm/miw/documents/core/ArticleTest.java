package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArticleTest {

    @Test
    public void testArticleBuilder() {
        Article article = Article.builder().code("8400000000017").reference("ref-art1").description("desc-art1").retailPrice("10.6")
                .build();
        assertEquals("8400000000017", article.getCode());
        assertEquals(new Integer(0), article.getStock());
    }

}
