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

import es.upm.miw.dtos.ArticleFamiliaOutputDto;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.repositories.core.ArticleFamilyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Autowired
    ArticleFamilyRepository articleFamilyRepository;

    @Test
    public void testReadAllArticles() {

    }



}
