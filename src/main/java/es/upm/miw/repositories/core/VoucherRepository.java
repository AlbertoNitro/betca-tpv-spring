package es.upm.miw.repositories.core;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Voucher;

public interface VoucherRepository extends MongoRepository<Voucher, String> {

}
