package es.upm.miw.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.dtos.IncomeComparision;
import es.upm.miw.dtos.NumProductsSoldDto;
import es.upm.miw.repositories.core.TicketRepository;

@Service
public class StatisticsDataService {

	@Autowired
	private TicketRepository ticketRepository;

	public List<HistoricalProductOutPutDto> GetHistoricalData(Date initDate, Date endDate) {

		List<HistoricalProductOutPutDto> result = new ArrayList<HistoricalProductOutPutDto>();

		List<Ticket> tickectsCollection = ticketRepository.findByCreationDateBetween(initDate, endDate);

		Calendar initCalendar = Calendar.getInstance();
		initCalendar.setTime(initDate);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int initMonth = initCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONDAY);

		if (endCalendar.get(Calendar.YEAR) > initCalendar.get(Calendar.YEAR)) {
			endMonth = endCalendar.get(Calendar.MONDAY)
					+ 12 * (endCalendar.get(Calendar.YEAR) - initCalendar.get(Calendar.YEAR));
		}

		HashMap<String, HashMap<Integer, Integer>> resultAux = new HashMap<String, HashMap<Integer, Integer>>();
		for (Ticket ticket : tickectsCollection) {

			Calendar ticketCalendar = Calendar.getInstance();
			ticketCalendar.setTime(ticket.getCreationDate());
			Integer ticketMonth = ticketCalendar.get(Calendar.MONTH);

			for (Shopping shopping : ticket.getShoppingList()) {

				Article articleAux = shopping.getArticle();
				if (!resultAux.containsKey(articleAux.getReference())) {

					HashMap<Integer, Integer> mapAux = new HashMap<Integer, Integer>();
					mapAux.put(ticketMonth, shopping.getAmount());
					resultAux.put(articleAux.getReference(), mapAux);

				} else {
					HashMap<Integer, Integer> mapAux = resultAux.get(articleAux.getReference());
					if (!mapAux.containsKey(ticketMonth)) {
						mapAux.put(ticketMonth, +shopping.getAmount());
					} else {
						mapAux.put(ticketMonth, ((int) mapAux.get(ticketMonth)) + shopping.getAmount());
					}
				}
			}
		}

		for (Map.Entry<String, HashMap<Integer, Integer>> articleData : resultAux.entrySet()) {

			List<Integer> numArticlePerMonthCollection = new ArrayList<Integer>();
			HashMap<Integer, Integer> value = articleData.getValue();

			for (int i = initMonth; i <= endMonth; i++) {

				if (value.containsKey(i))
					numArticlePerMonthCollection.add(value.get(i));

				else
					numArticlePerMonthCollection.add(0);
			}

			HistoricalProductOutPutDto item = new HistoricalProductOutPutDto(numArticlePerMonthCollection,
					articleData.getKey());
			result.add(item);
		}

		return result;
	}

	public List<NumProductsSoldDto> GetNumProductsSold(Date initDate, Date endDate) {
		List<NumProductsSoldDto> result = new ArrayList<NumProductsSoldDto>();

		List<Ticket> tickectsCollection = ticketRepository.findByCreationDateBetween(initDate, endDate);

		HashMap<String, Integer> mapAux = new HashMap<String, Integer>();
		for (Ticket ticket : tickectsCollection) {

			for (Shopping shopping : ticket.getShoppingList()) {

				Article articleAux = shopping.getArticle();
				if (!mapAux.containsKey(articleAux.getReference())) {

					mapAux.put(articleAux.getReference(), shopping.getAmount());

				} else {

					mapAux.put(articleAux.getReference(),
							((int) mapAux.get(articleAux.getReference())) + shopping.getAmount());

				}
			}
		}
		for (Map.Entry<String, Integer> articleData : mapAux.entrySet()) {
			result.add(new NumProductsSoldDto(articleData.getValue(), articleData.getKey()));
		}

		return result;
	}

	public List<IncomeComparision> GetIncomeComparisionData(Date initDate, Date endDate) {

		List<IncomeComparision> result = new ArrayList<IncomeComparision>();

		List<Ticket> tickectsCollection = ticketRepository.findByCreationDateBetween(initDate, endDate);

		HashMap<String, Float> mapAuxProductPrice = new HashMap<String, Float>();
		HashMap<String, Float> mapAuxIncome = new HashMap<String, Float>();
		for (Ticket ticket : tickectsCollection) {

			for (Shopping shopping : ticket.getShoppingList()) {

				Article articleAux = shopping.getArticle();
				BigDecimal productPrice = BigDecimal.valueOf(shopping.getAmount())
						.multiply(articleAux.getRetailPrice());
				BigDecimal income = shopping.getDiscount().intValue() > 0 ? productPrice.divide(shopping.getDiscount())
						: productPrice;

				if (!mapAuxProductPrice.containsKey(articleAux.getReference())
						&& !mapAuxIncome.containsKey(articleAux.getReference())) {

					mapAuxIncome.put(articleAux.getReference(), income.floatValue());
					mapAuxProductPrice.put(articleAux.getReference(), productPrice.floatValue());

				} else {

					mapAuxProductPrice.put(articleAux.getReference(),
							mapAuxProductPrice.get(articleAux.getReference()) + productPrice.floatValue());
					mapAuxIncome.put(articleAux.getReference(),
							mapAuxIncome.get(articleAux.getReference()) + income.floatValue());

				}
			}
		}

		for (Map.Entry<String, Float> incomeItem : mapAuxIncome.entrySet()) {
			result.add(new IncomeComparision(mapAuxProductPrice.get(incomeItem.getKey()), incomeItem.getValue(),
					incomeItem.getKey()));
		}

		return result;

	}

}
