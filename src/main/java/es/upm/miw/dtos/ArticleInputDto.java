package es.upm.miw.dtos;

import java.math.BigDecimal;

public class ArticleInputDto {

    private String code;

	private String reference;

    private String description;

    private BigDecimal retailPriceMin;
    
	private BigDecimal retailPriceMax;

    private String provider;

    public ArticleInputDto() {
        // Empty for framework
    }
    
    public ArticleInputDto(String code, String reference, String description, BigDecimal retailPriceMin, BigDecimal retailPriceMax, String provider) {
		super();
		this.code = code;
		this.reference = reference;
		this.description = description;
		this.retailPriceMin = retailPriceMin;
		this.retailPriceMax = retailPriceMax;
		this.provider = provider;
	}
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRetailPriceMin() {
		return retailPriceMin;
	}

	public void setRetailPriceMin(BigDecimal retailPriceMin) {
		this.retailPriceMin = retailPriceMin;
	}

	public BigDecimal getRetailPriceMax() {
		return retailPriceMax;
	}

	public void setRetailPriceMax(BigDecimal retailPriceMax) {
		this.retailPriceMax = retailPriceMax;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "ArticleInputDto [code=" + code + ", reference=" + reference + ", description=" + description
				+ ", retailPriceMin=" + retailPriceMin + ", retailPriceMax=" + retailPriceMax + ", provider=" + provider
				+ "]";
	}


}