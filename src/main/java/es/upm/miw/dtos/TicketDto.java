package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;

@JsonInclude(Include.NON_NULL)
public class TicketDto {

    private String id;

    private Date creationDate;

    private BigDecimal debt;

    private String note;

    private UserDto user;

    @NotNull
    private List<ShoppingDto> shoppingList;

    public TicketDto() {
        // Empty for framework
    }

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.creationDate = ticket.getCreationDate();
        this.debt = ticket.getDebt();
        this.note = ticket.getNote();
        if (ticket.getUser() == null) {
            this.user = null;
        } else {
            this.user = new UserDto(ticket.getUser());
        }
        shoppingList = new ArrayList<ShoppingDto>();
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingList.add(new ShoppingDto(shopping));
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

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public List<ShoppingDto> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingDto> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TicketDto [id=" + id + ", creationDate=" + creationDate + ", debt=" + debt + ", note=" + note + ", user=" + user
                + ", shoppingList=" + shoppingList + "]";
    }

}
