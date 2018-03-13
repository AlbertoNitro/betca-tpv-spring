package es.upm.miw.controllers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Budget;
import es.upm.miw.dtos.BudgetCreationInputDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.repositories.core.BudgetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class BudgetControllerIT {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetController budgetController;

    @Test
    public void testCreateBudget() {
        List<ShoppingDto> shoppingList = new ArrayList<ShoppingDto>();
        shoppingList.add(new ShoppingDto("1", "various", new BigDecimal("100"), 1, new BigDecimal("50.00"), new BigDecimal("50"), false));
        BudgetCreationInputDto budgetCreationDto = new BudgetCreationInputDto(shoppingList);
        shoppingList.add(new ShoppingDto("1", "various", new BigDecimal("50"), 1, new BigDecimal("50"), new BigDecimal("50"), false));
        BudgetCreationInputDto budgetCreationDto2 = new BudgetCreationInputDto(shoppingList);
        this.budgetController.createBudget(budgetCreationDto);
        Budget budget1 = this.budgetRepository.findFirstByOrderByCreationDateDescIdDesc();
        this.budgetController.createBudget(budgetCreationDto2);
        Budget budget2 = this.budgetRepository.findFirstByOrderByCreationDateDescIdDesc();

        assertEquals(budget1.simpleId() + 1, budget2.simpleId());

        this.budgetRepository.delete(budget1);
        this.budgetRepository.delete(budget2);
    }

}
