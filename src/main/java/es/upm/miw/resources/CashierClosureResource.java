package es.upm.miw.resources;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.dtos.input.CashierClosureDto;
import es.upm.miw.dtos.output.CashierClosureLastDto;

@RestController
@RequestMapping(CashierClosureResource.CASHIER_CLOUSURES)
public class CashierClosureResource {

    public static final String CASHIER_CLOUSURES = "/cashier-clousures";

    public static final String LAST = "/last";

    @RequestMapping(value = LAST, method = RequestMethod.GET)
    public CashierClosureLastDto getCashierClosureLastDate() {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createCashierClosure() {
    }

    @RequestMapping(value = LAST, method = RequestMethod.PATCH)
    public void closeCashierClosure(@RequestBody CashierClosureDto cashierClosureDto) {
    }

}
