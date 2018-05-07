package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ProviderMinimumDto;

public interface ProviderRepository extends MongoRepository<Provider, String> {

    public Provider findByCompany(String company);

    @Query(value = "{}", fields = "{ company : 1}") // by default: _id
    public List<ProviderMinimumDto> findMinimumAll();

    @Query(value = "{active : true}", fields = "{ company : 1}") // by default: _id
    public List<ProviderMinimumDto> findMinimumAllActives();

}
