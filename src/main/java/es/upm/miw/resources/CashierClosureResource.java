package es.upm.miw.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.CashierClosureController;
import es.upm.miw.dtos.input.CashierClosureDto;
import es.upm.miw.dtos.output.CashierClosureLastDto;
import es.upm.miw.resources.exceptions.CashierClosedException;
import es.upm.miw.resources.exceptions.CashierCreateException;
import es.upm.miw.resources.exceptions.FieldInvalidException;


@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CashierClosureResource.CASHIER_CLOSURES)
public class CashierClosureResource {

    public static final String CASHIER_CLOSURES = "/cashier-closures";

    public static final String LAST = "/last";

    @Autowired
    private CashierClosureController cashierClosureController;

    @RequestMapping(value = LAST, method = RequestMethod.GET)
    public CashierClosureLastDto getCashierClosureLast() {
        return cashierClosureController.getCashierClosureLast();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createCashierClosure() throws CashierCreateException {
        Optional<String> error = cashierClosureController.createCashierClosure();
        if (error.isPresent()) {
            throw new CashierCreateException(error.get());
        }
    }

    @RequestMapping(value = LAST, method = RequestMethod.PATCH)
    public void closeCashierClosure(@RequestBody CashierClosureDto cashierClosureDto) throws  CashierClosedException, FieldInvalidException {
        this.validateFieldObject(cashierClosureDto, "No se ha enviado el cierre de caja");
        this.validateFieldObject(cashierClosureDto.getFinalCash(), "Final Cash invalido");
        this.validateFieldObject(cashierClosureDto.getSalesCard(), "Sales Card invalido");
        Optional<String> error = cashierClosureController.close(cashierClosureDto);        
        if (error.isPresent()) {
            throw new CashierClosedException(error.get());
        }
    }

    private void validateFieldObject(Object objeto, String msg) throws FieldInvalidException{
        if (objeto == null) {
            throw new FieldInvalidException(msg);
        }
    }

}
