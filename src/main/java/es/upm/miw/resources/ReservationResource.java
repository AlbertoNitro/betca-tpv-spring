package es.upm.miw.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ReservationController;
import es.upm.miw.dtos.ReservationCreationInputDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ReservationResource.RESERVATIONS)
public class ReservationResource {

	public static final String RESERVATIONS = "/reservations";
	
	public static final String ID_ID = "/{id}";
	
	@Autowired
	private ReservationController reservationController;

	@PostMapping(produces = { "application/pdf", "application/json" })
	public byte[] createReservation(@Valid @RequestBody ReservationCreationInputDto reservationCreationInputDto)
			throws FieldInvalidException {
		return this.reservationController.createReservationAndPdf(reservationCreationInputDto)
				.orElseThrow(() -> new FieldInvalidException("Article exception"));
	}

}
