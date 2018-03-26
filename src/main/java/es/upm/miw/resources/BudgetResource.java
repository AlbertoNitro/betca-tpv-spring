package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.BudgetController;
import es.upm.miw.dtos.BudgetDto;
import es.upm.miw.resources.exceptions.BudgetIdNotFoundException;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(BudgetResource.BUDGETS)
public class BudgetResource {

    public static final String BUDGETS = "/budgets";

    public static final String ID_ID = "/{id}";

    @Autowired
    private BudgetController budgetController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public byte[] createBudget(@Valid @RequestBody BudgetDto budgetCreationDto) throws FieldInvalidException {
        return this.budgetController.createBudget(budgetCreationDto).orElseThrow(() -> new FieldInvalidException("Article exception"));
    }

    @GetMapping
    public List<BudgetDto> readBudgetAll() {
        return this.budgetController.readBudgetAll();
    }

    @GetMapping(value = ID_ID, produces = {"application/pdf", "application/json"})
    public byte[] read(@PathVariable String id) throws BudgetIdNotFoundException {
        return this.budgetController.read(id).orElseThrow(() -> new BudgetIdNotFoundException(id));
    }
}
