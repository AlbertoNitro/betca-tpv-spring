package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import es.upm.miw.utils.Encrypting;

@Document
public class Reservation {
	  private static final String DATE_FORMAT = "yyyyMMdd-";

	    @Id
	    private String id;

	    private Date creationDate;

	    private String reference;

	    private Shopping[] shoppingList;

	    private BigDecimal cashDeposited;
	    
	    private ReservationState reservationState; 

	    @DBRef
	    private User user;

	    public Reservation() {
	        this.creationDate = new Date();
	        this.reference = new Encrypting().encryptInBase64UrlSafe();
	    }

	    public Reservation(int idOfday, BigDecimal cashDeposited, Shopping[] shoppingList, User user) {
	        this();
	        this.id = new SimpleDateFormat(DATE_FORMAT).format(new Date()) + idOfday;
	        this.cashDeposited = cashDeposited;
	        this.shoppingList = shoppingList;
	        this.user = user;
	        this.reservationState = ReservationState.OPEN;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public int simpleId() {
	        return Integer.parseInt(String.valueOf(id).substring(DATE_FORMAT.length()));
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

	    public BigDecimal getCashDeposited() {
	        return cashDeposited;
	    }
	    
	    public void setCashDeposited(BigDecimal cashDeposited) {
	        this.cashDeposited = cashDeposited;
	    }
	    
	    public ReservationState getReservationState() {
	        return reservationState;
	    }

	    public void setReservationState(ReservationState reservationState) {
	        this.reservationState = reservationState;
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
	        return id.hashCode();
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
	        return id.equals(((Reservation) obj).id);
	    }
	    
	    @Override
	    public String toString() {
	        String createTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(this.creationDate.getTime());
	        StringBuilder stringBuilder = new StringBuilder();
	        stringBuilder.append("Reservation[" + id + ": created=" + createTime + ", reference=" + reference + ", reservationState=" + reservationState +
	        		", shoppingList=" + Arrays.toString(shoppingList) + ", cashDeposited=" + cashDeposited);
	        if (user != null) {
	            stringBuilder.append(", userId=" + user.getMobile());
	        }
	        stringBuilder.append("]");
	        return stringBuilder.toString();
	    }
}
