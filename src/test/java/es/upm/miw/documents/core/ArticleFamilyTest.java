package es.upm.miw.documents.core;

import static org.junit.Assert.assertEquals;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleFamily;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ArticleFamilyTest {

    @Test
    public void CratArtileFamily() {

        List<Article> listArticles = new ArrayList<Article>();
        listArticles.add(new Article("Reference1", "Descripcion1", 123));
        ArticleFamily articleFamily = new ArticleFamily("FamilyArt", listArticles);

        assertEquals("ArticleFamily [id=null, reference=FamilyArt]", articleFamily.toString());

    }

}
