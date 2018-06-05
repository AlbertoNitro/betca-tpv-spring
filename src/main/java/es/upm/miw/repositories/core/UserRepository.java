package es.upm.miw.repositories.core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserMinimumDto;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByMobile(String mobile);

    public User findByEmail(String email);

    public User findByDni(String dni);

    @Query("{ 'token.value' : ?0 }")
    public User findByTokenValue(String tokenValue);

    @Query(value = "{'roles' : 'CUSTOMER'}", fields = "{ '_id' : 0, 'mobile' : 1, 'username' : 1}")
    public List<UserMinimumDto> findCustomerAll();

    @Query(value = "{$and:["
            + "{'roles' : 'CUSTOMER'},"
            + "?#{ [0] == null ? { $where : 'true'} : { 'mobile' :   {$regex:[0]} } },"
            + "?#{ [1] == null ? { $where : 'true'} : { 'username' : {$regex:[1], $options: 'i'} } },"
            + "?#{ [2] == null ? { $where : 'true'} : { 'dni' :      {$regex:[2], $options: 'i'} } },"
            + "?#{ [3] == null ? { $where : 'true'} : { 'address' :  {$regex:[3], $options: 'i'} } }"
            + "]}", fields = "{ '_id' : 0, 'mobile' : 1, 'username' : 1}")
    List<UserMinimumDto> findCustomersByMobileLikeAndUsernameLikeAndDniLikeAndAddressLike(String mobile, String username, String dni, String address);

}
