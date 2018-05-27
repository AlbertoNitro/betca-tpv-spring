package es.upm.miw.businessControllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.exceptions.FieldAlreadyExistException;
import es.upm.miw.exceptions.ForbiddenException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.core.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto, Role[] roles) throws FieldAlreadyExistException {
        if (this.userRepository.findByMobile(userDto.getMobile()) != null) {
            throw new FieldAlreadyExistException("User mobile (" + userDto.getMobile() + ")");
        }
        this.assertEmailNotRepeated(userDto.getMobile(), userDto);
        this.assertDniNotRepeat(userDto.getMobile(), userDto);
        User user = new User(userDto.getMobile(), userDto.getUsername(), userDto.getPassword(), userDto.getDni(), userDto.getAddress(),
                userDto.getEmail());
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    private void assertEmailNotRepeated(String mobile, UserDto userDto) throws FieldAlreadyExistException {
        if (userDto.getEmail() != null && !userDto.getEmail().trim().isEmpty()) {
            User user = this.userRepository.findByEmail(userDto.getEmail());
            if (user != null && !user.getMobile().equals(mobile)) {
                throw new FieldAlreadyExistException("User email (" + userDto.getEmail() + ")");
            }
        }

    }

    private void assertDniNotRepeat(String mobile, UserDto userDto) throws FieldAlreadyExistException {
        if (userDto.getDni() != null && !userDto.getDni().trim().isEmpty()) {
            User user = this.userRepository.findByDni(userDto.getDni());
            if (user != null && !user.getMobile().equals(mobile)) {
                throw new FieldAlreadyExistException("User dni (" + userDto.getDni() + ")");
            }
        }
    }

    public UserDto readUser(String mobile, Role[] roles) throws NotFoundException, ForbiddenException {
        User user = this.userRepository.findByMobile(mobile);
        if (user == null) {
            throw new NotFoundException("User mobile (" + mobile + ")");
        }
        if (!Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
            throw new ForbiddenException("User mobile (" + mobile + ")");
        }
        return new UserDto(user);
    }

    public UserDto readUser(String mobile, String mobileLogged) throws NotFoundException, ForbiddenException {
        User userLogged = this.userRepository.findByMobile(mobileLogged);
        return this.readUser(mobile, userLogged.getRoles());
    }

    public void updateUser(String mobile, UserDto userDto, Role[] roles)
            throws NotFoundException, FieldAlreadyExistException, ForbiddenException {
        User user = this.userRepository.findByMobile(mobile);
        if (user == null) {
            throw new NotFoundException("User mobile (" + mobile + ")");
        }
        if (!mobile.equals(userDto.getMobile()) && this.userRepository.findByMobile(userDto.getMobile()) != null) {
            throw new FieldAlreadyExistException("User mobile (" + userDto.getMobile() + ")");
        }
        this.assertEmailNotRepeated(mobile, userDto);
        this.assertDniNotRepeat(mobile, userDto);
        if (!Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
            throw new ForbiddenException("User mobile (" + mobile + ")");
        }
        user.setMobile(userDto.getMobile());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setDni(userDto.getDni());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setActive(userDto.isActive());
        user.setRoles(userDto.getRoles());
        this.userRepository.save(user);
    }

    public void updateOwnUser(String mobile, UserDto userDto, String mobileLogged)
            throws NotFoundException, FieldAlreadyExistException, ForbiddenException {
        User userLogged = this.userRepository.findByMobile(mobileLogged);
        this.updateUser(mobile, userDto, userLogged.getRoles());
    }

    public void deleteUser(String mobile, Role[] roles) throws ForbiddenException {
        User user = this.userRepository.findByMobile(mobile);
        if (user != null) {
            if (Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
                this.userRepository.delete(user);
            } else {
                throw new ForbiddenException("User mobile (" + mobile + ")");
            }
        }
    }

    public List<UserMinimumDto> readCustomerAll() {
        return this.userRepository.findCustomerAll();
    }

    public UserMinimumDto username(String mobile) {
        User user = this.userRepository.findByMobile(mobile);
        return new UserMinimumDto(user.getMobile(), user.getUsername());
    }

    public List<UserMinimumDto> find(String mobile, String username, String dni, String address) {
        return this.userRepository.findCustomersByMobileLikeAndUsernameLikeAndDniLikeAndAddressLike(mobile, username, dni, address);
    }

}
