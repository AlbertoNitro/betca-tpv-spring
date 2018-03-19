package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;

public interface TicketRepository extends MongoRepository<Ticket, String> {

	Ticket findByReference(String reference);

	List<Ticket> findByUserOrderByCreationDateDesc(User user);

	List<Ticket> findByCreationDateGreaterThan(Date date);

	Ticket findFirstByOrderByCreationDateDescIdDesc();

	List<Ticket> findByCreationDateBetween(Date initialDate, Date finalDate);

	@Query(value = "{'$and':[{'shoppingList': {'$elemMatch' :{'article.$id': ?0}}}, {creationDate:{ $gte: ?1, $lt: ?2}}]}", fields = "{'creationDate' :1, 'shoppingList.$' :1}")
	List<Ticket> findByIdAndDatesBetween(String id, Date dateStart, Date dateFinish);

}
