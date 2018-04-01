package es.upm.miw.dtos;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.Provider;

@JsonInclude(Include.NON_NULL)
public class OrderDto {

    private String id;

    private String Provider_id;

    private String Provider_name;

    private Date Order_date;

    public OrderDto() {
    }

    public OrderDto(String id, String id_provider, String provider_name) {
        this.id = id;
        this.Provider_id = id_provider;
        this.Provider_name = provider_name;
    }

    public OrderDto(Order order, Provider provider) {
        this.id = order.getId();
        this.Provider_id = provider.getId();
        this.Provider_name = provider.getCompany();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider_id() {
        return Provider_id;
    }

    public void setProvider_id(String provider_id) {
        Provider_id = provider_id;
    }

    public String getProvider_name() {
        return Provider_name;
    }

    public void setProvider_name(String provider_name) {
        Provider_name = provider_name;
    }

    public Date getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(Date order_date) {
        Order_date = order_date;
    }

    @Override
    public String toString() {
        return "OrderDto [id=" + id + ", Provider_id=" + Provider_id + ", Provider_name=" + Provider_name + ", Order_date=" + Order_date
                + "]";
    }

}
