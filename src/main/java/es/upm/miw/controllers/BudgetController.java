package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.dtos.BudgetDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.BudgetRepository;
import es.upm.miw.services.PdfService;
import es.upm.miw.utils.Encrypting;

@Controller
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createBudget(BudgetDto budgetCreationDto) {
        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingDto shoppingDto : budgetCreationDto.getShoppingCart()) {
            Article article = this.articleRepository.findOne(shoppingDto.getCode());
            if (article == null) {
                return Optional.empty();
            }
            Shopping shopping = new Shopping(shoppingDto.getAmount(), shoppingDto.getDiscount(), article);
            shoppingList.add(shopping);
        }
        Budget budget = new Budget(shoppingList.toArray(new Shopping[0]));
        String id;
        do {
            id = new Encrypting().shortId64UrlSafe();
        } while (this.budgetRepository.findOne(id) != null);
        budget.setId(id);
        this.budgetRepository.save(budget);
        return pdfService.generateBudget(budget);
    }

    public List<BudgetDto> readBudgetAll() {
        List<Budget> budgetList = this.budgetRepository.findAll();
        List<BudgetDto> budgetDtoList = new ArrayList<>();
        for (Budget budget : budgetList) {
            BudgetDto budgetDto = new BudgetDto(budget.getId(), null);
            budgetDtoList.add(budgetDto);
        }
        return budgetDtoList;
    }

    public Optional<byte[]> read(String id) {
        Budget budget = this.budgetRepository.findOne(id);
        if (budget != null) {
            return pdfService.generateBudget(budget);
        } else {
            return Optional.empty();
        }
    }
}
