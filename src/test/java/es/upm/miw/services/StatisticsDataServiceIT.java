package es.upm.miw.services;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.dtos.IncomeComparision;
import es.upm.miw.dtos.NumProductsSoldDto;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class StatisticsDataServiceIT {

	@Autowired
	private StatisticsDataService statisticsDataService;

	@Test
	public void TestGetHistoricalData() {
		Boolean result = false;
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.add(Calendar.DATE, 7);
		today.add(Calendar.MONTH, -1);
		Date initDate = today.getTime();
		today.add(Calendar.MONTH, 1);
		Date endDate = today.getTime();

		List<HistoricalProductOutPutDto> historicalData = statisticsDataService.GetHistoricalData(initDate, endDate);

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

		List<NumProductsSoldDto> data = statisticsDataService.GetNumProductsSold(initDate, endDate);

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

		List<IncomeComparision> data = statisticsDataService.GetIncomeComparisionData(initDate, endDate);

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
