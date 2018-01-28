package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.users.User;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    
    List<User> findByUserOrderByCreationDateDesc(User user);
    
}
