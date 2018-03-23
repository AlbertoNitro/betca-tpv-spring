package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ArticleFamiliaOutputDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleFamilyControllerIT {

    @Autowired
    ArticleFamilyController articleFamilyControlle;

    @Test
    public void testGetAllComponent() {
        int sizeGetAllComponent = this.articleFamilyControlle.getAllComponent().count();
        assertEquals(sizeGetAllComponent, sizeGetAllComponent);
    }

    @Test
    public void testGetListaComponent() {
        this.articleFamilyControlle.getListaComponent();
        int sizeGetAllComponent = this.articleFamilyControlle.getListaComponent().size();
        assertEquals(sizeGetAllComponent, sizeGetAllComponent);
    }

    @Test
    public void testGetListaCompositeFamily() {
        ArticleFamiliaOutputDto sizeGetListaCompositeFamily = this.articleFamilyControlle.getListaCompositeFamily();
        int count = sizeGetListaCompositeFamily.getListArticles().size();
        assertEquals(ArticleFamiliaOutputDto.class, sizeGetListaCompositeFamily.getClass());
        assertEquals(sizeGetListaCompositeFamily.getListArticles().size(), count);
    }

}
