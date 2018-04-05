package es.upm.miw.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.ShoppingState;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.repositories.core.TicketRepository;


@Controller
public class ArticlesTrackingController {
	@Autowired
	private TicketRepository ticketRepository;

	
	public Optional<TicketDto> findTicketNotCommitted(String id) {
		System.out.println("El id es: " + id);
		Ticket ticket = this.ticketRepository.findById(id);
    	//System.out.println("El id es: " + ticket.getId());
		if(ticket == null) {
			System.out.println("El ticket es null " );
		} 
		  
    	for (int i = 0; i < ticket.getShoppingList().length; i++) {
	        if (ticket != null && (ticket.getShoppingList()[i].getShoppingState() != ShoppingState.COMMITTED)) {      
	        	System.out.println("Tiene un estado !COMMMIT");
	        	return Optional.of(new TicketDto(ticket));
	        } else {
	        	System.out.println("Tiene un estado COMMMIT");
	            
	        }
    	}
    	 
    	return Optional.empty();
    
    } 

}
