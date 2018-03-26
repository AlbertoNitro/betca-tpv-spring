package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Voucher;

public interface VoucherRepository extends MongoRepository<Voucher, String> {
    
    List<Voucher> findByDateOfUseGreaterThan(Date date);

    List<Voucher> findByCreationDateGreaterThan(Date date);
}
