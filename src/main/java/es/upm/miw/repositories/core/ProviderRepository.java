package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ProviderMinimumDto;

public interface ProviderRepository extends MongoRepository<Provider, String> {
    
    public Provider findById(String id);
    
    public Provider findByCompany(String company);
    
    @Query(value = "{}", fields = "{ '_id' : 1, 'company' : 1}")
    public List<ProviderMinimumDto> findMinimumAll();
    
    @Query(value = "{'active' : true}", fields = "{ '_id' : 1, 'company' : 1}")
    public List<ProviderMinimumDto> findMinimumAllActives();

    @Query(value = "{'active' : true, '_id': ?0}", fields = "{'company' : 1}")
    public ProviderMinimumDto findMinimumProviderById(String id);

}
