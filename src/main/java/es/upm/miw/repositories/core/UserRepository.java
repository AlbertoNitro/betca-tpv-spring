package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserMinimumDto;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'mobile' : ?0 }", fields = "{ 'username' : 1}")
    public User findUsernameByMobile(String mobile);

    // @Query("{ 'mobile' : ?0 }")
    public User findByMobile(String mobile);

    public User findByEmail(String email);

    public User findByDni(String dni);

    @Query("{ 'token.value' : ?0 }")
    public User findByTokenValue(String tokenValue);

    @Query(value = "{'roles' : 'CUSTOMER'}", fields = "{ '_id' : 0, 'mobile' : 1, 'username' : 1}")
    public List<UserMinimumDto> findCustomerAll();
}
