package es.upm.miw.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.dtos.BudgetCreationInputDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.BudgetRepository;
import es.upm.miw.services.PdfService;

@Controller
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createBudget(BudgetCreationInputDto budgetCreationDto) {
        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingDto shoppingDto : budgetCreationDto.getShoppingCart()) {
            Article article = this.articleRepository.findOne(shoppingDto.getCode());
            if (article == null) {
                return Optional.empty();
            }
            Shopping shopping = new Shopping(shoppingDto.getAmount(), shoppingDto.getDiscount(), article);
            if (shoppingDto.isCommitted()) {
                shopping.setShoppingState(ShoppingState.COMMITTED);
            } else {
                shopping.setShoppingState(ShoppingState.NOT_COMMITTED);
            }
            shoppingList.add(shopping);
        }
        Budget budget = new Budget(this.nextId(), shoppingList.toArray(new Shopping[0]));
        this.budgetRepository.save(budget);
        return pdfService.generateBudget(budget);
    }

    private int nextId() {
        int nextId = 1;
        Budget budget = budgetRepository.findFirstByOrderByCreationDateDescIdDesc();
        if (budget != null) {
            Date startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (budget.getCreationDate().compareTo(startOfDay) >= 0) {
                nextId = budget.simpleId() + 1;
            }
        }
        return nextId;
    }
}
