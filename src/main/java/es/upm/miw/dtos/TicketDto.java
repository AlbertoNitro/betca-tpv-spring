package es.upm.miw.dtos;

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

    @NotNull
    private List<ShoppingDto> shoppingList;

    public TicketDto() {
        // Empty for framework
    }

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.creationDate = ticket.getCreationDate();
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

    public List<ShoppingDto> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingDto> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString() {
        return "TicketDto [id=" + id + ", creationDate=" + creationDate + ", shoppingList=" + shoppingList + "]";
    }

}
