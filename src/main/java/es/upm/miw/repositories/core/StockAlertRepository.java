package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.StockAlert;
import es.upm.miw.dtos.StockAlertMinimumDto;

public interface StockAlertRepository extends MongoRepository<StockAlert, String> {

    public StockAlert findByName(String name);
    
    @Query(value = "{}", fields = "{ '_id' : 0, 'name' : 1}")
    public List<StockAlertMinimumDto> findStockAlertAll();
    
}
