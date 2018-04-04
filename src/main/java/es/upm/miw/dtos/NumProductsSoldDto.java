package es.upm.miw.dtos;

public class NumProductsSoldDto {

	private Integer Quantity;
	private String ProductName;

	public NumProductsSoldDto() {
		super();
		//Empty for Spring
	}

	public NumProductsSoldDto(Integer quantity, String productName) {
		super();
		Quantity = quantity;
		ProductName = productName;
	}

	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

}
