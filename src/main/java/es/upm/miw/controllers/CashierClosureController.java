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
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.CashierClosureInputDto;
import es.upm.miw.dtos.CashierClosureLastOutputDto;
import es.upm.miw.dtos.CashierClosureSearchOutputDto;
import es.upm.miw.repositories.core.CashMovementRepository;
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
	
	@Autowired
	private CashMovementRepository cashMovementRepository;

	public CashierClosureLastOutputDto getCashierClosureLast() {
		CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
		if (cashierClosure != null) {
			return new CashierClosureLastOutputDto(cashierClosure);
		} else {
			cashierClosure = new CashierClosure(new BigDecimal("0"));
			cashierClosure.close(new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"),
					"Initial");
			this.cashierClosureRepository.save(cashierClosure);
			return new CashierClosureLastOutputDto(cashierClosure);
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
			return Optional.of("Already opened: " + lastCashierClosure.getId());
		}
	}

	public Optional<String> close(CashierClosureInputDto cashierClosureDto) {
		CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();

		if (lastCashierClosure != null && !lastCashierClosure.isClosed()) {
			Date cashierOpenedDate = cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getOpeningDate();
			lastCashierClosure.close(cashierClosureDto.getFinalCash(), this.salesCash(cashierOpenedDate),
					cashierClosureDto.getSalesCard(), this.usedVouchers(cashierOpenedDate),
					cashierClosureDto.getComment());
			this.cashierClosureRepository.save(lastCashierClosure);
			return Optional.empty();

		} else {
			return Optional.of("Already closed");
		}
	}
	
	public CashierClosureSearchOutputDto getTotalCardAndCash() {
		CashierClosure lastCashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
		CashierClosureSearchOutputDto cashierClosureDto = 
				new CashierClosureSearchOutputDto(new BigDecimal("0"), new BigDecimal("0"));
		
		if (lastCashierClosure != null && !lastCashierClosure.isClosed()) {
			Date cashierOpenedDate = cashierClosureRepository.findFirstByOrderByOpeningDateDesc().getOpeningDate();
			
			BigDecimal totalCash = new BigDecimal("0"), totalCard = new BigDecimal("0");
			totalCard = totalCard.add(cashDeposited(cashierOpenedDate));
			totalCard = totalCard.add(cashMovements(cashierOpenedDate));
			
			totalCash = totalCash.add(salesCash(cashierOpenedDate));
			totalCash = totalCash.subtract(cashMovements(cashierOpenedDate));
			
			cashierClosureDto.setTotalCard(totalCard);
			cashierClosureDto.setTotalCash(totalCash);
		}
		return cashierClosureDto;
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

	public List<CashierClosureSearchOutputDto> readDatesAll(Date startDate, Date endDate) {
		List<CashierClosure> salesList = this.cashierClosureRepository.findByDateBetween(startDate, endDate);
		List<CashierClosureSearchOutputDto> salesListDto = new ArrayList<CashierClosureSearchOutputDto>();
		for (CashierClosure sales : salesList) {
			salesListDto.add(new CashierClosureSearchOutputDto(sales.getSalesCash(), sales.getSalesCard(),
					sales.getClosureDate()));
		}
		return salesListDto;
	}

}
