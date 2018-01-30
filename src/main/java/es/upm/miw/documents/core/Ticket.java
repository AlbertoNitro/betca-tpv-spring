package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import es.upm.miw.utils.Encrypting;

@Document
public class Ticket {

    private static final String DATE_FORMAT = "yyyyMMdd";

    @Id
    private String id;

    private Date creationDate;

    private String reference;

    private Shopping[] shoppingList;

    @DBRef
    private User user;

    public Ticket() {
        this.creationDate = new Date();
        this.reference = new Encrypting().encryptInBase64UrlSafe();
    }

    public Ticket(int idOfday) {
        this();
        String date = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
        this.id = date + "-" + idOfday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long simpleId() {
        return Long.parseLong(String.valueOf(id).substring(DATE_FORMAT.length() + 1));
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getTicketTotal() {
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
        return (id == ((Ticket) obj).id);
    }

    @Override
    public String toString() {
        String createTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(this.creationDate.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ticket[" + id + ": created=" + createTime + ", shoppingList=" + shoppingList);
        if (user != null) {
            stringBuilder.append(", userId=" + user.getMobile());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
