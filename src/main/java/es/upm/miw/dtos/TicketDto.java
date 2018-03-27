package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;

@Document
public class TicketDto {

    @Id
    private String id;

    private Date creationDate;

    private String reference;

    private BigDecimal cashDeposited;

    private List<ShoppingOutputDto> shoppingList;

    public TicketDto() {
        // Empty for framework
    }

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.creationDate = ticket.getCreationDate();
        shoppingList = new ArrayList<ShoppingOutputDto>();
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingList.add(new ShoppingOutputDto(shopping));
        }     
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public List<ShoppingOutputDto> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingOutputDto> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((cashDeposited == null) ? 0 : cashDeposited.hashCode());
        result = (prime * result) + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result) + ((reference == null) ? 0 : reference.hashCode());
        result = (prime * result) + ((shoppingList == null) ? 0 : shoppingList.hashCode());
        return result;
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
        TicketDto other = (TicketDto) obj;
        if (cashDeposited == null) {
            if (other.cashDeposited != null) {
                return false;
            }
        } else if (!cashDeposited.equals(other.cashDeposited)) {
            return false;
        }
        if (creationDate == null) {
            if (other.creationDate != null) {
                return false;
            }
        } else if (!creationDate.equals(other.creationDate)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (reference == null) {
            if (other.reference != null) {
                return false;
            }
        } else if (!reference.equals(other.reference)) {
            return false;
        }
        if (shoppingList == null) {
            if (other.shoppingList != null) {
                return false;
            }
        } else if (!shoppingList.equals(other.shoppingList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketDto [id=" + id + ", creationDate=" + creationDate + ", reference=" + reference + ", cashDeposited=" + cashDeposited
                + ", shoppingList=" + shoppingList + "]";
    }

}
