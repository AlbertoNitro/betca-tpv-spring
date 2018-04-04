package es.upm.miw.repositories.core;

import es.upm.miw.documents.core.Scheduler;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchedulerRepository extends MongoRepository<Scheduler, String> {


}
