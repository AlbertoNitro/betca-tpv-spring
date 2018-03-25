package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import es.upm.miw.documents.core.Offer;

public interface OfferRepository extends MongoRepository<Offer, String> {

}
