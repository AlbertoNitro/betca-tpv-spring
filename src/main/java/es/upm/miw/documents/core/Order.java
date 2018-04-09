package es.upm.miw.documents.core;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

    @Id
    private String id;

    private String description;

    @DBRef
    private Provider provider;

    private Date openingDate;

    private Date closingDate;

    private OrderLine[] orderLine;

    public Order(String description, Provider provider, OrderLine[] orderLine) {
        this.openingDate = new Date();
        this.description = description;
        this.provider = provider;
        this.orderLine = orderLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderLine[] getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine[] orderLine) {
        this.orderLine = orderLine;
    }

    public String getId() {
        return id;
    }

    public Provider getProvider() {
        return provider;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", description=" + description + ", provider=" + provider + ", openingDate=" + openingDate
                + ", closingDate=" + closingDate + ", orderLine=" + orderLine + "]";
    }

    public void close() {
        this.closingDate = new Date();
    }

}
