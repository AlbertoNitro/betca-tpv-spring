package es.upm.miw.repositories.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.dtos.VoucherDto;

public interface VoucherRepository extends MongoRepository<Voucher, String> {
	
	public Voucher findByReference(String reference);

    List<Voucher> findByDateOfUseGreaterThan(Date date);

}
