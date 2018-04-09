package es.upm.miw.dtos;

import java.util.Date;

import es.upm.miw.documents.core.Order;

public class OrderBaseOutputDto {

    private String id;

    private String description;

    private String providerCompany;

    private Date openingDate;

    public OrderBaseOutputDto() {
    }

    public OrderBaseOutputDto(Order order) {
        this.id= order.getId();
        this.description= order.getDescription();
        this.providerCompany= order.getProvider().getCompany();
        this.openingDate= order.getOpeningDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderCompany() {
        return providerCompany;
    }

    public void setProviderCompany(String providerCompany) {
        this.providerCompany = providerCompany;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @Override
    public String toString() {
        return "OrderBaseOutputDto [id=" + id + ", description=" + description + ", providerCompany=" + providerCompany + ", openingDate="
                + openingDate + "]";
    }

}
