package es.upm.miw.resources;

import java.math.BigDecimal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.CashMovementController;
import es.upm.miw.dtos.CashMovementDto;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(CashMovementResource.CASH_MOVEMENTS)
public class CashMovementResource {

	public static final String CASH_MOVEMENTS = "/cash-movements";

	@Autowired
	private CashMovementController cashMovementController;

	@PostMapping
	public void createCashMovement(@Valid @RequestBody CashMovementDto cashMovementDto) {

		if (cashMovementDto.getValue() == null) {
			cashMovementDto.setValue(new BigDecimal(99));
		}

		this.cashMovementController.createCashMovement(cashMovementDto);
	}
}
