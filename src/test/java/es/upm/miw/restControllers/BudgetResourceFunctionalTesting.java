package es.upm.miw.restControllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.BudgetDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.restControllers.BudgetResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class BudgetResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testCreateBudget() {
        List<ShoppingDto> shoppingList = new ArrayList<ShoppingDto>();
        shoppingList.add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), false));
        shoppingList.add(new ShoppingDto("1", "various", new BigDecimal("50"), 1, new BigDecimal("50"), new BigDecimal("50"), false));
        BudgetDto budgetCreationDto = new BudgetDto(shoppingList);
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(BudgetResource.BUDGETS).body(budgetCreationDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateBudgetShoppingNullException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        BudgetDto budgetCreationDto = new BudgetDto();
        budgetCreationDto.setShoppingCart(null);
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(BudgetResource.BUDGETS).body(budgetCreationDto)
                .clazz(byte[].class).post().build();
    }

    @Test
    public void testCreateBudgetShoppingEmptyException() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        BudgetDto budgetCreationDto = new BudgetDto(new ArrayList<ShoppingDto>());
        restService.loginAdmin().restBuilder(new RestBuilder<byte[]>()).path(BudgetResource.BUDGETS).body(budgetCreationDto)
                .clazz(byte[].class).post().build();
    }

}
