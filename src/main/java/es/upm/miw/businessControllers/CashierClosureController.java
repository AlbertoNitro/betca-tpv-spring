package es.upm.miw.businessControllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierLastOutputDto;
import es.upm.miw.dtos.ClosedCashierOutputDto;
import es.upm.miw.exceptions.BadRequestException;
import es.upm.miw.exceptions.NotFoundException;
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

    public void createCashierClosure() throws BadRequestException {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        assert lastCashierClosure != null;
        if (!lastCashierClosure.isClosed()) {
            throw new BadRequestException("Already opened: " + lastCashierClosure.getId());
        }
        this.cashierClosureRepository.save(new CashierClosure(lastCashierClosure.getFinalCash()));
    }

    public void createCashierMovement(CashierMovementInputDto cashierMovementInputDto) throws NotFoundException, BadRequestException {
        if (this.readCashierClosureLast().isClosed()) {
            throw new BadRequestException("Cashier closed");
        }
        User user = this.userRepository.findByMobile(cashierMovementInputDto.getAuthorMobile());
        if (user == null) {
            throw new NotFoundException("Mobile (" + cashierMovementInputDto.getAuthorMobile() + ")");
        }

        CashMovement cashMovement = new CashMovement(cashierMovementInputDto.getValue(), cashierMovementInputDto.getComment(), user);
        this.cashMovementRepository.save(cashMovement);
    }

    public CashierLastOutputDto readCashierClosureLast() {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        assert lastCashierClosure != null;
        return new CashierLastOutputDto(lastCashierClosure);
    }

    public CashierClosingOutputDto readTotalsFromLast() throws BadRequestException {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        assert lastCashierClosure != null;
        if (lastCashierClosure.isClosed()) {
            throw new BadRequestException("Cashier already closed: " + lastCashierClosure.getId());
        }
        CashierClosingOutputDto cashierClosingOutputDto = new CashierClosingOutputDto();
        BigDecimal totalVoucher = this.totalVouchers(lastCashierClosure.getOpeningDate());
        cashierClosingOutputDto.setTotalVoucher(totalVoucher);
        cashierClosingOutputDto.setTotalCard(lastCashierClosure.getSalesCard());
        cashierClosingOutputDto.setTotalCash(lastCashierClosure.getInitialCash().add(lastCashierClosure.getSalesCash())
                .add(this.cashMovements(lastCashierClosure.getOpeningDate())));
        cashierClosingOutputDto.setSalesTotal(lastCashierClosure.getSalesCash().add(lastCashierClosure.getSalesCard().add(totalVoucher)));
        return cashierClosingOutputDto;
    }

    public void close(CashierClosureInputDto cashierClosureDto) throws BadRequestException {
        CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        assert lastCashierClosure != null;
        if (lastCashierClosure.isClosed()) {
            throw new BadRequestException("Cashier already closed: " + lastCashierClosure.getId());
        }
        Date cashierOpenedDate = cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getOpeningDate();
        lastCashierClosure.close(cashierClosureDto.getSalesCard(), cashierClosureDto.getFinalCash(), this.usedVouchers(cashierOpenedDate),
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
    }

    private BigDecimal usedVouchers(Date cashierOpenedDate) {
        List<Voucher> usedVoucherlist = voucherRepository.findByDateOfUseGreaterThan(cashierOpenedDate);
        BigDecimal total = BigDecimal.ZERO;
        for (Voucher voucher : usedVoucherlist) {
            total = total.add(voucher.getValue());
        }
        List<Voucher> createdVoucherlist = voucherRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        for (Voucher voucher : createdVoucherlist) {
            total = total.subtract(voucher.getValue());
        }
        return total;
    }

    private BigDecimal cashMovements(Date cashierOpenedDate) {
        List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
        BigDecimal total = BigDecimal.ZERO;
        for (CashMovement cashMovement : cashMovementList) {
            total = total.add(cashMovement.getValue());
        }
        return total;
    }

    private BigDecimal totalVouchers(Date cashierOpenedDate) {
        BigDecimal total = BigDecimal.ZERO;
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

    public List<ClosedCashierOutputDto> findBetweenDate(Date start, Date end) {
        List<CashierClosure> cashierClosureList = this.cashierClosureRepository
                .findByOpeningDateBetweenAndClosureDateNotNullOrderByClosureDateDesc(start, end);
        List<ClosedCashierOutputDto> cashierClosureClosedOutputDto = new ArrayList<ClosedCashierOutputDto>();
        for (CashierClosure cashierClosure : cashierClosureList) {
            cashierClosureClosedOutputDto.add(new ClosedCashierOutputDto(cashierClosure));
        }
        return cashierClosureClosedOutputDto;
    }

}
