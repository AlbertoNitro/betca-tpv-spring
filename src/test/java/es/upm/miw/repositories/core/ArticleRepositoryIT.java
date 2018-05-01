package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.ArticleDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ArticleRepositoryIT {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void testFindOne() {
        assertEquals("8400000000017", articleRepository.findOne("8400000000017").getCode());
    }

    @Test
    public void testFindMinimumByCode() {
        ArticleDto articleOutputDto = articleRepository.findMinimumByCode("8400000000017");
        assertEquals("8400000000017", articleOutputDto.getCode());
        assertEquals("ref-a1", articleOutputDto.getReference());
        assertEquals("descrip-a1", articleOutputDto.getDescription());
        assertEquals(new BigDecimal("20"), articleOutputDto.getRetailPrice());
    }

    @Test
    public void testFindByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider() {
        this.articleRepository.findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider("", "", "provider1");
    }

    @Test
    public void testFindByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull() {
        this.articleRepository
                .findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();
    }

}
