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

    List<Ticket> findByCreationDateGreaterThanOrderByCreationDateDesc(Date date);

    Ticket findFirstByOrderByCreationDateDescIdDesc();

    List<Ticket> findByCreationDateBetweenOrderByCreationDateDesc(Date initialDate, Date finalDate);

    Ticket findFirstByUserOrderByCreationDateDesc(User user);

    @Query(value = 
        " { shoppingList: " + "{$elemMatch :  "
        +    "{ $and:["
        +       "{article.$id: { $in : ?0 } },"
        +       "{shoppingState:'NOT_COMMITTED'}"
        +    "] }"
        + "} }")
    List<Ticket> findByShoopingListArticleIdNotCommited(String[] article);
    
    @Query(value = " { shoppingList: " + "{$elemMatch :  {shoppingState:'NOT_COMMITTED'} } }")
    List<Ticket> findByTicketsNotCommited();
   
    @Query(value = "{'$and':[{'shoppingList': {'$elemMatch' :{'article.$id': ?0}}}, {creationDate:{ $gte: ?1, $lt: ?2}}]}", fields = "{'creationDate' :1, 'shoppingList.$' :1}")
    List<Ticket> findByIdArticleDatesBetween(String id, Date dateStart, Date dateFinish);

}
