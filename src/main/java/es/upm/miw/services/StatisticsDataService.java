package es.upm.miw.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.TicketRepository;

@Service
public class StatisticsDataService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ArticleRepository articleRepository;

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
						mapAux.put(ticketMonth,
								((int) mapAux.get(ticketMonth)) + shopping.getAmount());
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
}
