package es.upm.miw.documents.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

    @Id
    private String id;

    private String id_provider;

    private Date order_date;

    @DBRef
    private List<OrderBody> orderBody;

    public Order() {
        // TODO Auto-generated constructor stub
    }

    public Order(String id, Date orderDate) {
        super();
        this.id = id;
        this.order_date = orderDate;
    }

    public Order(String id, String id_provider, Date order_date) {
        super();
        this.id = id;
        this.id_provider = id_provider;
        this.order_date = order_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_provider() {
        return id_provider;
    }

    public void setId_provider(String id_provider) {
        this.id_provider = id_provider;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public List<OrderBody> getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(List<OrderBody> orderBody) {
        this.orderBody = orderBody;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", id_provider=" + id_provider + ", order_date=" + order_date + ", orderBody=" + orderBody + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((id_provider == null) ? 0 : id_provider.hashCode());
        result = prime * result + ((orderBody == null) ? 0 : orderBody.hashCode());
        result = prime * result + ((order_date == null) ? 0 : order_date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id_provider == null) {
            if (other.id_provider != null)
                return false;
        } else if (!id_provider.equals(other.id_provider))
            return false;
        if (orderBody == null) {
            if (other.orderBody != null)
                return false;
        } else if (!orderBody.equals(other.orderBody))
            return false;
        if (order_date == null) {
            if (other.order_date != null)
                return false;
        } else if (!order_date.equals(other.order_date))
            return false;
        return true;
    }

}

