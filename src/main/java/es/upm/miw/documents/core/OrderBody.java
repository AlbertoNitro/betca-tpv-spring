package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OrderBody {

    @Id
    private String id;

    private String id_order;

    private String id_article;

    public OrderBody() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((id_article == null) ? 0 : id_article.hashCode());
        result = prime * result + ((id_order == null) ? 0 : id_order.hashCode());
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
        OrderBody other = (OrderBody) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id_article == null) {
            if (other.id_article != null)
                return false;
        } else if (!id_article.equals(other.id_article))
            return false;
        if (id_order == null) {
            if (other.id_order != null)
                return false;
        } else if (!id_order.equals(other.id_order))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderBody [id=" + id + ", id_order=" + id_order + ", id_article=" + id_article + "]";
    }

}
