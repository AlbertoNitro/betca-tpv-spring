package es.upm.miw.resources;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.upm.miw.controllers.CashierClosureController;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierClosureLastOutputDto;
import es.upm.miw.dtos.CashierClosureSearchOutputDto;
import es.upm.miw.dtos.CashierMovementInputDto;
import es.upm.miw.resources.exceptions.CashierClosedException;
import es.upm.miw.resources.exceptions.CashierCreateException;
import es.upm.miw.resources.exceptions.CashierMovementException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CashierClosureResource.CASHIER_CLOSURES)
public class CashierClosureResource {

    public static final String CASHIER_CLOSURES = "/cashier-closures";

    public static final String LAST = "/last";

    public static final String SEARCH = "/search";

    public static final String TOTALS = "/totals";

    public static final String MOVEMENTS = "/movements";

    @Autowired
    private CashierClosureController cashierClosureController;

    @GetMapping(value = LAST)
    public CashierClosureLastOutputDto getCashierClosureLast() {
        return cashierClosureController.getCashierClosureLast();
    }

    @PostMapping(value = LAST + MOVEMENTS)
    public void createCashMovement(@Valid @RequestBody CashierMovementInputDto cashierMovementDto) throws CashierMovementException {
        Optional<String> error = this.cashierClosureController.createCashierMovement(cashierMovementDto);
        if (error.isPresent()) {
            throw new CashierMovementException(error.get());
        }
    }

    @RequestMapping(value = LAST + TOTALS, method = RequestMethod.GET)
    public CashierClosureSearchOutputDto readTotalsFromLast() throws CashierClosedException {
        return this.cashierClosureController.readTotalsFromLast().orElseThrow(()-> new CashierClosedException("Cashier already closed"));
    }

    @PostMapping
    public void openCashierClosure() throws CashierCreateException {
        Optional<String> error = cashierClosureController.openCashierClosure();
        if (error.isPresent()) {
            throw new CashierCreateException(error.get());
        }
    }

    @PatchMapping(value = LAST)
    public void closeCashierClosure(@Valid @RequestBody CashierClosureInputDto cashierClosureDto) throws CashierClosedException {
        Optional<String> error = cashierClosureController.close(cashierClosureDto);
        if (error.isPresent()) {
            throw new CashierClosedException(error.get());
        }
    }

    @RequestMapping(value = SEARCH, method = RequestMethod.GET)
    public List<CashierClosureSearchOutputDto> findSalesByDateBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("dateStart") Date dateStart,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "dateFinish") Date dateFinish) {
        return this.cashierClosureController.findSalesByDateBetween(dateStart, dateFinish);
    }

}
