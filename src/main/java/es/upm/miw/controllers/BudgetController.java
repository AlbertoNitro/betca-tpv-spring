package es.upm.miw.controllers;

import java.util.ArrayList;
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
import es.upm.miw.services.PdfService;

@Controller
public class BudgetController {

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
        Budget budget = new Budget(111, shoppingList.toArray(new Shopping[0]));
        return pdfService.generateBudget(budget);
    }
}
