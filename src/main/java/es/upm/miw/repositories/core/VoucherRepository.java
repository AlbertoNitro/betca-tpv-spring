package es.upm.miw.repositories.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.upm.miw.documents.core.Voucher;

public interface VoucherRepository extends MongoRepository<Voucher, String> {

    List<Voucher> findByValue(BigDecimal value);

    List<Voucher> findByDateOfUseGreaterThan(Date date);

}
