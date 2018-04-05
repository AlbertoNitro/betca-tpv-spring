package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

	Reservation findFirstByOrderByCreationDateDescIdDesc();
}
