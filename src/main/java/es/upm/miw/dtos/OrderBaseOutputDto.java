package es.upm.miw.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Order;

public class OrderBaseOutputDto {

    private String id;

    private String description;

    private String providerCompany;

    private Date openingDate;
    
    @JsonInclude(Include.NON_NULL)
    private Date closingDate;


    public OrderBaseOutputDto() {
    }

    public OrderBaseOutputDto(Order order) {
        this.id= order.getId();
        this.description= order.getDescription();
        this.providerCompany= order.getProvider().getCompany();
        this.openingDate= order.getOpeningDate();
        this.closingDate=order.getClosingDate();
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

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    @Override
    public String toString() {
        return "OrderBaseOutputDto [id=" + id + ", description=" + description + ", providerCompany=" + providerCompany + ", openingDate="
                + openingDate + ", closingDate=" + closingDate + "]";
    }

}
