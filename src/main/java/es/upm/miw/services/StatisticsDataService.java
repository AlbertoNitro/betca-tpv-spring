package es.upm.miw.services;

import java.util.ArrayList;
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

		HashMap<String, HashMap> resultAux = new HashMap<String, HashMap>();
		for (Ticket ticket : tickectsCollection) {
			for (Shopping shopping : ticket.getShoppingList()) {

				Article articleAux = shopping.getArticle();
				if (!resultAux.containsKey(articleAux.getReference())) {

					HashMap mapAux = new HashMap();
					mapAux.put(ticket.getCreationDate().getMonth(), 1);
					resultAux.put(articleAux.getReference(), mapAux);

				} else {
					HashMap mapAux = resultAux.get(articleAux.getReference());
					if (!mapAux.containsKey(ticket.getCreationDate().getMonth())) {
						mapAux.put(ticket.getCreationDate().getMonth(), 1);
					} else {
						mapAux.put(ticket.getCreationDate().getMonth(),
								((int) mapAux.get(ticket.getCreationDate().getMonth())) + 1);
					}
				}
			}
		}
		for (Map.Entry<String, HashMap> articleData : resultAux.entrySet()) {

			List<Integer> numArticlePerMonthCollection = new ArrayList<Integer>();
			HashMap<Integer, Integer> value = articleData.getValue();

			for (Map.Entry<Integer, Integer> item : value.entrySet()) {
				numArticlePerMonthCollection.add(item.getValue());
			}

			HistoricalProductOutPutDto item = new HistoricalProductOutPutDto(numArticlePerMonthCollection,
					articleData.getKey());
			result.add(item);
		}

		return result;
	}
}
