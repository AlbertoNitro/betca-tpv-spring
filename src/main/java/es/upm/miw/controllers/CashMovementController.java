package es.upm.miw.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.repositories.core.CashMovementRepository;

@Controller
public class CashMovementController {

	@Autowired
	private CashMovementRepository cashMovementRepository;

	public void createCashMovement(BigDecimal value) {
		CashMovement cashMovement = new CashMovement(value);
		this.cashMovementRepository.save(cashMovement);
	}
}
