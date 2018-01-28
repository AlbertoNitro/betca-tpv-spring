package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, Integer> {

}
