package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.ClosedCashierOutputDto;
import es.upm.miw.dtos.CashierClosingOutputDto;
import es.upm.miw.dtos.CashierMovementInputDto;
import es.upm.miw.repositories.core.CashMovementRepository;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.repositories.core.VoucherRepository;

@Controller
public class CashierClosureController {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CashMovementRepository cashMovementRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<String> createCashierMovement(CashierMovementInputDto cashierMovementInputDto) {
        if (this.getCashierClosureLast().isClosed()) {
            return Optional.of("Cashier closed");
        }
        User user = this.userRepository.findByMobile(cashierMovementInputDto.getAuthorMobile());
        if (user == null) {
            return Optional.of("Mobile not found: " + cashierMovementInputDto.getAuthorMobile());
        }

        CashMovement cashMovement = new CashMovement(cashierMovementInputDto.getValue(), cashierMovementInputDto.getComment(), user);
        this.cashMovementRepository.save(cashMovement);
        return Optional.empty();
    }

    public CashierLastOutputDto getCashierClosureLast() {
        CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (cashierClosure != null) {
            return new CashierLastOutputDto(cashierClosure);
        } else { // Sólo ocurre una sola vez en el despliegue con bd vacias
            cashierClosure = new CashierClosure(new BigDecimal("0"));
            cashierClosure.close(new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), "Initial");
            this.cashierClosureRepository.save(cashierClosure);
            return new CashierLastOutputDto(cashierClosure);
        }
    }

    public Optional<String> openCashierClosure() {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (lastCashierClosure == null) {
            this.cashierClosureRepository.save(new CashierClosure(new BigDecimal(0)));
            return Optional.empty();
        } else if (lastCashierClosure.isClosed()) {
            this.cashierClosureRepository.save(new CashierClosure(lastCashierClosure.getFinalCash()));
            return Optional.empty();
        } else {
            return Optional.of("Already opened: " + lastCashierClosure.getId());
        }
    }

    public Optional<String> close(CashierClosureInputDto cashierClosureDto) {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();

        if (lastCashierClosure != null && !lastCashierClosure.isClosed()) {
            Date cashierOpenedDate = cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getOpeningDate();
            lastCashierClosure.close(cashierClosureDto.getFinalCash(), this.salesCash(cashierOpenedDate), cashierClosureDto.getSalesCard(),
                    this.usedVouchers(cashierOpenedDate), cashierClosureDto.getComment());
            lastCashierClosure.setDeposit(BigDecimal.ZERO);
            lastCashierClosure.setWithdrawal(BigDecimal.ZERO);
            List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
            for (CashMovement cashMovement : cashMovementList) {
                if(cashMovement.getValue().signum()==1) {
                    lastCashierClosure.setDeposit(lastCashierClosure.getDeposit().add(cashMovement.getValue()));
                }else {
                    lastCashierClosure.setWithdrawal(lastCashierClosure.getWithdrawal().add(cashMovement.getValue().negate()));                   
                }
            }
            this.cashierClosureRepository.save(lastCashierClosure);
            return Optional.empty();

        } else {
            return Optional.of("Already closed");
        }
    }

    public Optional<CashierClosingOutputDto> readTotalsFromLast() {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();

        if (lastCashierClosure.isClosed()) {
            return Optional.empty();
        }

        Date cashierOpenedDate = lastCashierClosure.getOpeningDate();
        CashierClosingOutputDto cashierClosureDto = new CashierClosingOutputDto();
        
        cashierClosureDto.setTotalCash(lastCashierClosure.getInitialCash().add(this.cashDeposited(cashierOpenedDate)).add(this.cashMovements(cashierOpenedDate)));
        cashierClosureDto.setTotalVoucher(this.totalVouchers(cashierOpenedDate));
        cashierClosureDto.setTotalCard(this.salesCash(cashierOpenedDate).subtract(cashierClosureDto.getTotalVoucher())
                .subtract(this.cashDeposited(cashierOpenedDate)));
        return Optional.of(cashierClosureDto);
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

    private BigDecimal cashDeposited(Date cashierOpenedDate) {
        List<Ticket> ticketList = ticketRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (Ticket ticket : ticketList) {
            total = total.add(ticket.getCashDeposited());
        }
        return total;
    }

    private BigDecimal cashMovements(Date cashierOpenedDate) {
        List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (CashMovement cashMovement : cashMovementList) {
            total = total.add(cashMovement.getValue());
        }
        return total;
    }

    private BigDecimal totalVouchers(Date cashierOpenedDate) {
        List<Voucher> voucherList = voucherRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (Voucher voucher : voucherList) {
            total = total.add(voucher.getValue());
        }
        return total;
    }

    public List<CashierClosingOutputDto> findSalesByDateBetween(Date startDate, Date dateFinish) {
        List<CashierClosure> salesList = this.cashierClosureRepository.findSalesCashierClosureByDateBetween(startDate, dateFinish);
        List<CashierClosingOutputDto> salesListDto = new ArrayList<CashierClosingOutputDto>();
        for (CashierClosure sales : salesList) {
            salesListDto.add(new CashierClosingOutputDto(sales.getSalesCash(), sales.getSalesCard(), sales.getClosureDate()));
        }
        return salesListDto;
    }

    public List<ClosedCashierOutputDto> findBetweenDate(Date start, Date end) {
        List<CashierClosure> cashierClosureList = this.cashierClosureRepository.findByOpeningDateBetweenAndClosureDateNotNull(start, end);
        List<ClosedCashierOutputDto> cashierClosureClosedOutputDto = new ArrayList<ClosedCashierOutputDto>();
        for (CashierClosure ticket : cashierClosureList) {
            cashierClosureClosedOutputDto.add(new ClosedCashierOutputDto(ticket));
        }
        return cashierClosureClosedOutputDto;
    }

}
