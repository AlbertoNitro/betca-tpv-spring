package es.upm.miw.dtos;

public class IncomeComparision {

    private Float ProductPrice;

    private Float Income;

    private String ProductName;

    public IncomeComparision() {
        super();
        // Empty for Spring
    }

    public IncomeComparision(Float productPrice, Float income, String productName) {
        super();
        ProductPrice = productPrice;
        Income = income;
        ProductName = productName;
    }

    public Float getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Float productPrice) {
        ProductPrice = productPrice;
    }

    public Float getIncome() {
        return Income;
    }

    public void setIncome(Float income) {
        Income = income;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

}
