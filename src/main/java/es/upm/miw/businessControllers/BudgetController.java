package es.upm.miw.businessControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.businessServices.Encrypting;
import es.upm.miw.businessServices.PdfService;
import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Budget;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.dtos.BudgetDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.BudgetRepository;

@Controller
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createBudget(BudgetDto budgetCreationDto) throws NotFoundException {
        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingDto shoppingDto : budgetCreationDto.getShoppingCart()) {
            Article article = this.articleRepository.findOne(shoppingDto.getCode());
            if (article == null) {
                throw new NotFoundException("Article code (" + shoppingDto.getCode() + ")");
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

    public Optional<byte[]> read(String id) throws NotFoundException {
        Budget budget = this.budgetRepository.findOne(id);
        if (budget == null) {
            throw new NotFoundException("Budget id (" + id + ")");
        }
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

}
