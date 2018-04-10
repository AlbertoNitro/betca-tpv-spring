package es.upm.miw.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.dtos.IncomeComparision;
import es.upm.miw.dtos.NumProductsSoldDto;
import es.upm.miw.dtos.ShoppingDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.repositories.core.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TicketControllerIT {

	@Autowired
	private TicketController ticketController;

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	public void testCreateTicket() {
		TicketCreationInputDto ticketCreationInputDto = new TicketCreationInputDto(new BigDecimal("1150"),
				new BigDecimal("0"), new BigDecimal("0"), new ArrayList<ShoppingDto>());
		ticketCreationInputDto.getShoppingCart().add(new ShoppingDto("1", "various", new BigDecimal("100"), 1,
				new BigDecimal("50.00"), new BigDecimal("50"), false));
		ticketCreationInputDto.getShoppingCart().add(new ShoppingDto("1", "various", new BigDecimal("100"), 2,
				new BigDecimal("50.00"), new BigDecimal("100"), true));
		ticketCreationInputDto.getShoppingCart().add(new ShoppingDto("article2", "descrip-a2", new BigDecimal("10"),
				100, new BigDecimal("0"), new BigDecimal("1000"), true));
		this.ticketController.createTicketAndPdf(ticketCreationInputDto);
		Ticket ticket1 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();
		this.ticketController.createTicketAndPdf(ticketCreationInputDto);
		Ticket ticket2 = this.ticketRepository.findFirstByOrderByCreationDateDescIdDesc();

        System.out.println(">>>> 1:"+ticket1.simpleId());
        System.out.println(">>>> 2:"+ticket2.simpleId());
		
		assertEquals(ticket1.simpleId() + 1, ticket2.simpleId());

		this.ticketRepository.delete(ticket1);
		this.ticketRepository.delete(ticket2);
	}

	@Test
	public void testExistTicket() {
		assertTrue(this.ticketController.existTicket("201801121"));
		assertFalse(this.ticketController.existTicket("201801125"));
	}

	@Test
	public void testGetTicket() {
		Optional<TicketDto> pdf1 = this.ticketController.read("201801121");
		assertTrue(pdf1.isPresent());
		Optional<TicketDto> pdf2 = this.ticketController.read("201801122");
		assertTrue(pdf2.isPresent());
		Optional<TicketDto> pdf3 = this.ticketController.read("201801123");
		assertTrue(pdf3.isPresent());
	}

	@Test
	public void testFindByIdArticleDateBetween() {
		assertNotNull(this.ticketController.findByIdArticleDatesBetween("article1", new Date(), new Date()));
	}

	@Test
	public void TestGeHistoricalProductsDataBetweenDates() {
		Boolean result = false;
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.add(Calendar.DATE, 7);
		today.add(Calendar.MONTH, -1);
		Date initDate = today.getTime();
		today.add(Calendar.MONTH, 1);
		Date endDate = today.getTime();

		List<HistoricalProductOutPutDto> historicalData = ticketController
				.getHistoricalProductsDataBetweenDates(initDate, endDate);

		List<String> articleNamesCollection = Arrays.asList("ar11", "ar12");
		for (HistoricalProductOutPutDto item : historicalData) {
			if (articleNamesCollection.contains(item.getProductName())) {
				if (item.getNumProductsPerMonth().contains(6)) {
					result = true;
				} else
					result = false;
			}
		}
		assertTrue(result);
	}

	@Test
	public void TestGetNumProductsSold() {
		Boolean result = false;
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.add(Calendar.DATE, 7);
		today.add(Calendar.MONTH, -1);
		Date initDate = today.getTime();
		today.add(Calendar.MONTH, 1);
		Date endDate = today.getTime();

		List<NumProductsSoldDto> data = ticketController.getNumProductsSold(initDate, endDate);

		List<String> articleNamesCollection = Arrays.asList("ar11", "ar12");
		for (NumProductsSoldDto item : data) {
			if (articleNamesCollection.contains(item.getProductName())) {
				if (item.getQuantity() == 6) {
					result = true;
				} else
					result = false;
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void TestGetIncomeComparisionData() {
		Boolean result = false;
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.add(Calendar.DATE, 7);
		today.add(Calendar.MONTH, -1);
		Date initDate = today.getTime();
		today.add(Calendar.MONTH, 1);
		Date endDate = today.getTime();

		List<IncomeComparision> data = ticketController.getIncomeComparisionData(initDate, endDate);

		List<String> articleNamesCollection = Arrays.asList("ar11", "ar12", "ar13");
		float expectedIncome = 120;
		float expectedProductPrice = 150;
		for (IncomeComparision item : data) {
			if (articleNamesCollection.contains(item.getProductName())) {
				if (item.getProductName().equals("ar13") && item.getIncome().equals(expectedIncome)
						&& item.getProductPrice().equals(expectedProductPrice)) {
					result = true;
				} else if (item.getIncome().equals(item.getProductPrice())) {
					result = true;
				} else {
					result = false;
					break;
				}
			}
		}
		assertTrue(result);
	}

}
