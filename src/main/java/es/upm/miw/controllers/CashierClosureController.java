package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.ClosedCashierOutputDto;
import es.upm.miw.dtos.CashierClosingOutputDto;
import es.upm.miw.dtos.CashierMovementInputDto;
import es.upm.miw.repositories.core.CashMovementRepository;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.repositories.core.VoucherRepository;

@Controller
public class CashierClosureController {

    @Autowired
    private CashierClosureRepository cashierClosureRepository;

    @Autowired
    private VoucherRepository voucherRepository;

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
            cashierClosure = new CashierClosure(BigDecimal.ZERO);
            cashierClosure.close(BigDecimal.ZERO, BigDecimal.ZERO, "Initial");
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
            lastCashierClosure.close(cashierClosureDto.getFinalCash(), this.usedVouchers(cashierOpenedDate),
                    cashierClosureDto.getComment());
            lastCashierClosure.setDeposit(BigDecimal.ZERO);
            lastCashierClosure.setWithdrawal(BigDecimal.ZERO);
            List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
            for (CashMovement cashMovement : cashMovementList) {
                if (cashMovement.getValue().signum() == 1) {
                    lastCashierClosure.setDeposit(lastCashierClosure.getDeposit().add(cashMovement.getValue()));
                } else {
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

        cashierClosureDto.setTotalCash(lastCashierClosure.getInitialCash().add(lastCashierClosure.getSalesCash())
                .add(this.cashMovements(cashierOpenedDate)).setScale(2, RoundingMode.HALF_UP));
        cashierClosureDto.setTotalVoucher(this.totalVouchers(cashierOpenedDate).setScale(2, RoundingMode.HALF_UP));
        cashierClosureDto.setTotalCard(lastCashierClosure.getSalesCard().setScale(2, RoundingMode.HALF_UP));
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

    private BigDecimal cashMovements(Date cashierOpenedDate) {
        List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = new BigDecimal("0");
        for (CashMovement cashMovement : cashMovementList) {
            total = total.add(cashMovement.getValue());
        }
        return total;
    }

    private BigDecimal totalVouchers(Date cashierOpenedDate) {
        BigDecimal total = new BigDecimal("0");
        List<Voucher> voucherCreatedList = voucherRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        for (Voucher voucher : voucherCreatedList) {
            total = total.subtract(voucher.getValue());
        }
        List<Voucher> voucherConsumedList = voucherRepository.findByDateOfUseGreaterThan(cashierOpenedDate);
        for (Voucher voucher : voucherConsumedList) {
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
        List<CashierClosure> cashierClosureList = this.cashierClosureRepository
                .findByOpeningDateBetweenAndClosureDateNotNullOrderByClosureDateDesc(start, end);
        List<ClosedCashierOutputDto> cashierClosureClosedOutputDto = new ArrayList<ClosedCashierOutputDto>();
        for (CashierClosure ticket : cashierClosureList) {
            cashierClosureClosedOutputDto.add(new ClosedCashierOutputDto(ticket));
        }
        return cashierClosureClosedOutputDto;
    }

}
