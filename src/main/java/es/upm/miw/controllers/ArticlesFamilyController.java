package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.dtos.ArticlesFamiliaOutputDto;

@Controller
public class ArticlesFamilyController {
    public Optional<List<ArticlesFamiliaOutputDto>> readArticlesFamily(String id) {
        List<ArticlesFamiliaOutputDto> articlesFamiliaOutputDtoList = new ArrayList<>();
        
        int ini = 0;
        if (!id.equals("root")) {
            ini = Integer.parseInt(id);
        }
        for (int i = ini; i < ini + 5; i++) {
            articlesFamiliaOutputDtoList
                    .add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.ARTICLE, ini + 2 - i));
        }
        for (int i = ini + 5; i < ini + 8; i++) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.ARTICLES, null));
        }
        for (int i = ini + 8; i < ini + 10; i++) {
            articlesFamiliaOutputDtoList.add(new ArticlesFamiliaOutputDto("" + i, "REF" + i, "descripción" + i, FamilyType.SIZES, null));
        }

        return Optional.of(articlesFamiliaOutputDtoList);
    }

}
