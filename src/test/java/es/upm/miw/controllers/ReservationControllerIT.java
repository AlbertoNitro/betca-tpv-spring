package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Reservation;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.ReservationCreationInputDto;
import es.upm.miw.repositories.core.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class ReservationControllerIT {

	@Autowired
	private ReservationController reservationController;

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	public void testCreateTicket() {
		ReservationCreationInputDto reservationCreationInputDto = new ReservationCreationInputDto(new BigDecimal("1150"),
				new BigDecimal("0"), new BigDecimal("0"), new ArrayList<ShoppingDto>());
		reservationCreationInputDto.getShoppingCart().add(new ShoppingDto("1", "various", new BigDecimal("100"), 1,
				new BigDecimal("50.00"), new BigDecimal("50"), false));
		reservationCreationInputDto.getShoppingCart().add(new ShoppingDto("1", "various", new BigDecimal("100"), 2,
				new BigDecimal("50.00"), new BigDecimal("100"), true));
		reservationCreationInputDto.getShoppingCart().add(new ShoppingDto("article2", "descrip-a2", new BigDecimal("10"),
				100, new BigDecimal("0"), new BigDecimal("1000"), true));
		this.reservationController.createReservationAndPdf(reservationCreationInputDto);
		Reservation reservation1 = this.reservationRepository.findFirstByOrderByCreationDateDescIdDesc();
		this.reservationController.createReservationAndPdf(reservationCreationInputDto);
		Reservation reservation2 = this.reservationRepository.findFirstByOrderByCreationDateDescIdDesc();

		
		assertEquals(reservation1.simpleId() + 1, reservation2.simpleId());

		this.reservationRepository.delete(reservation1);
		this.reservationRepository.delete(reservation2);
	}



}
