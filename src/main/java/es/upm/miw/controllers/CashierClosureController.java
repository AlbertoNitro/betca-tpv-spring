package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.dtos.input.CashierClosureDto;
import es.upm.miw.dtos.output.CashierClosureLastDto;
import es.upm.miw.repositories.core.CashierClosureRepository;

@Controller
public class CashierClosureController {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;

    public CashierClosureLastDto getCashierClosureLast() {
        CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (cashierClosure != null) {
            return new CashierClosureLastDto(cashierClosure);
        } else {
            return new CashierClosureLastDto(true, null);
        }
    }

    public Optional<String> createCashierClosure() {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (lastCashierClosure == null) {
            this.cashierClosureRepository.save(new CashierClosure(new BigDecimal(0)));
            return Optional.empty();
        } else if (lastCashierClosure.isClosed()) {
            this.cashierClosureRepository.save(new CashierClosure(lastCashierClosure.getFinalCash()));
            return Optional.empty();
        } else {
            return Optional.of("Opened: " + lastCashierClosure.getId());
        }
    }

    public Optional<String> close(CashierClosureDto cashierClosureDto) {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (lastCashierClosure != null && !lastCashierClosure.isClosed()) {
            lastCashierClosure.close(cashierClosureDto.getFinalCash(), cashierClosureDto.getSalesCard(), cashierClosureDto.getComment());
            this.cashierClosureRepository.save(lastCashierClosure);
            return Optional.empty();
        } else {
            return Optional.of("Closed");
        }
    }

}
