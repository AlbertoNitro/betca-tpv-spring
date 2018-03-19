package es.upm.miw.dtos;

import java.util.List;

public class HistoricalProductOutPutDto {

	private List<Integer> NumProductsPerMonth;
	private String ProductName;

	public HistoricalProductOutPutDto(List<Integer> numProductsPerMonth, String productName) {
		super();
		this.NumProductsPerMonth = numProductsPerMonth;
		ProductName = productName;
	}

	public List<Integer> getNumProductsPerMonth() {
		return NumProductsPerMonth;
	}

	public void setNumProductsPerMonth(List<Integer> numProductsPerMonth) {
		NumProductsPerMonth = numProductsPerMonth;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

}
