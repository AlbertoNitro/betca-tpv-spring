package es.upm.miw.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;

public class TicketOutputDto {

    private String id;

    private Date creationDate;

    private List<ShoppingOutputDto> shoppingList;

    public TicketOutputDto() {
        // Empty for framework
    }

    public TicketOutputDto(Ticket ticket) {
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

    public List<ShoppingOutputDto> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingOutputDto> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString() {
        return "TicketDto [id=" + id + ", creationDate=" + creationDate + ", shoppingList=" + shoppingList + "]";
    }

}
