package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

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
    public void testReadAllArticlesFamily() {
//        List<ArticleOutputDto> articleOutputDtoList = Arrays
//                .asList(restService.loginAdmin().restBuilder(new RestBuilder<ArticleOutputDto[]>()).clazz(ArticleOutputDto[].class)
//                        .path(ArticleFamilyResource.ARTICLESFAMILY).get().build());
//
//        assertEquals(articleOutputDtoList.size(), articleOutputDtoList.size());
    }
}
