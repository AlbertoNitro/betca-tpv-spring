package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Budget {

    @Id
    private String id;

    private Date creationDate;

    private Shopping[] shoppingList;

    public Budget() {
        this.creationDate = new Date();
    }

    public Budget(Shopping[] shoppingList) {
        this();
        this.shoppingList = shoppingList;
    }

    public String getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Shopping[] getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Shopping[] shoppingList) {
        this.shoppingList = shoppingList;
    }

    //TODO
    public BigDecimal getBudgetTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Shopping shopping : shoppingList) {
            total = total.add(shopping.getShoppingTotal());
        }
        return total;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (id.equals(((Budget) obj).id));
    }

    @Override
    public String toString() {
        return "Budget [id=" + id + ", creationDate=" + creationDate + ", shoppingList=" + Arrays.toString(shoppingList) + "]";
    }

}
