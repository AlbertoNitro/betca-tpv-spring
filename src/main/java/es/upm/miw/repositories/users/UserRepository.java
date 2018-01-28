package es.upm.miw.repositories.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.users.User;

public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'mobile' : ?0 }", fields = "{ 'username' : 1}")
    public User findUsernameByMobile(Long mobile);
    
    //@Query("{ 'mobile' : ?0 }")
    public User findByMobile(Long mobile);
    
    public User findByEmail(String email);

    public User findByDni(String dni);
    
    @Query("{ 'token.value' : ?0 }")    
    public User findByTokenValue(String tokenValue);
    
}
