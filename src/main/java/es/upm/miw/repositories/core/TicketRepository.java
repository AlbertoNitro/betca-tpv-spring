package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    
    List<Ticket> findByUserOrderByCreationDateDesc(User user);

    Ticket findByReference(String reference);
    
}
