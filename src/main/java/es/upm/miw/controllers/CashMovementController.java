package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.dtos.CashMovementDto;
import es.upm.miw.repositories.core.CashMovementRepository;

@Controller
public class CashMovementController {

	@Autowired
	private CashMovementRepository cashMovementRepository;

	public void createCashMovement(CashMovementDto cashMovementDto) {
		CashMovement cashMovement = new CashMovement(cashMovementDto.getValue(), cashMovementDto.getComment());
		this.cashMovementRepository.save(cashMovement);
	}
	
	public BigDecimal cashMovements(Date cashierOpenedDate) {
		List<CashMovement> cashMovementList = cashMovementRepository.findByCreationDateGreaterThan(cashierOpenedDate);
		BigDecimal total = new BigDecimal("0");
		for (CashMovement cashMovement : cashMovementList) {
			total = total.add(cashMovement.getValue());
		}
		return total;
	}
}
