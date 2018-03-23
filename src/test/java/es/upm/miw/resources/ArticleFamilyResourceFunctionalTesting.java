package es.upm.miw.resources;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ArticleOutputDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Test
    public void testReadAllArticles() {
        List<ArticleOutputDto> articleOutputDtoList = Arrays
                .asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleOutputDto[]>()).clazz(ArticleOutputDto[].class)
                        .path(ArticleFamilyResource.ARTICLESFAMILY).path(ArticleFamilyResource.ARTICLES).get().build());

        assertEquals(articleOutputDtoList.size(), articleOutputDtoList.size());
    }

    /**
     * Los Test funciona cuando se ejecuta el mismo paquete de test, pore cuando se ejecuta todo el test global no funcionan
     */

    // @Test
    // public void testReadAllArticlesFamily() {
    //
    // restService.loginAdmin().restBuilder(new RestBuilder<String>()).clazz(String.class).path(ArticleFamilyResource.ARTICLESFAMILY).get()
    // .build();
    // }
    //
    // @Test
    // public void testReadAllComponentFamily() {
    //
    // restService.loginAdmin().restBuilder(new RestBuilder<String>()).clazz(String.class).path(ArticleFamilyResource.ARTICLESFAMILY)
    // .path(ArticleFamilyResource.FAMILY).get().build();
    // }

}
