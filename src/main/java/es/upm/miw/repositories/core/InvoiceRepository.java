package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Ticket;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    
    Invoice findFirstOrderByCreationDateDesc();

    List<Invoice> findByCreationDateBetween(Date start, Date end);
    
    List<Invoice> findByTicketIn(List<Ticket> tickets);

    Invoice findByTicket(Ticket ticket);

}
