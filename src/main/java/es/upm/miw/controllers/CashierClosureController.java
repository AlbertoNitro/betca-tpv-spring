package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.input.CashierClosureDto;
import es.upm.miw.dtos.output.CashierClosureLastDto;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.VoucherRepository;

@Controller
public class CashierClosureController {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TicketRepository ticketRepository;

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

    //TODO realizar TEST
    public Optional<String> close(CashierClosureDto cashierClosureDto) {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();

        if (lastCashierClosure != null && !lastCashierClosure.isClosed()) {
            Date cashierOpenedDate = cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getOpeningDate();
            lastCashierClosure.close(cashierClosureDto.getFinalCash(), this.salesCash(cashierOpenedDate), cashierClosureDto.getSalesCard(),
                    this.usedVouchers(cashierOpenedDate), cashierClosureDto.getComment());
            this.cashierClosureRepository.save(lastCashierClosure);
            return Optional.empty();

        } else {
            return Optional.of("Closed");
        }
    }

    private BigDecimal usedVouchers(Date cashierOpenedDate) {
        List<Voucher> usedVoucherlist = voucherRepository.findByDateOfUseGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (Voucher voucher : usedVoucherlist) {
            total = total.add(voucher.getValue());
        }
        return total;
    }

    private BigDecimal salesCash(Date cashierOpenedDate) {
        List<Ticket> ticketList = ticketRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (Ticket ticket : ticketList) {
            total = total.add(ticket.getTicketTotal());
        }
        return total;
    }

}
