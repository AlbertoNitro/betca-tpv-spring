package es.upm.miw.controllers;

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
}
