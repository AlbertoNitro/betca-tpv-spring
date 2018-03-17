package es.upm.miw.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.BudgetController;
import es.upm.miw.dtos.BudgetDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(BudgetResource.BUDGETS)
public class BudgetResource {

    public static final String BUDGETS = "/budgets";

    @Autowired
    private BudgetController budgetController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public @ResponseBody byte[] createBudget(@Valid @RequestBody BudgetDto budgetCreationDto) throws FieldInvalidException {
        Optional<byte[]> pdf = this.budgetController.createBudget(budgetCreationDto);
        if (!pdf.isPresent()) {
            throw new FieldInvalidException("Article exception");
        } else {
            return pdf.get();
        }
    }
    
    @GetMapping
    public List<BudgetDto> readBudgetAll() {
        return this.budgetController.readBudgetAll();
    }
}
