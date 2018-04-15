package es.upm.miw.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.repositories.core.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto, Role[] roles) {
        User user = new User(userDto.getMobile(), userDto.getUsername(), userDto.getPassword(), userDto.getDni(), userDto.getAddress(),
                userDto.getEmail());
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    public boolean mobileRepeated(String oldMobile, UserDto userDto) {
        User user = this.userRepository.findByMobile(userDto.getMobile());
        return user != null && !user.getMobile().equals(oldMobile);
    }

    public boolean dniRepeated(String mobile, UserDto userDto) {
        if (userDto.getDni() == null || userDto.getDni().trim().isEmpty()) {
            return false;
        }
        User user = this.userRepository.findByDni(userDto.getDni());
        return userDto.getDni() != null && user != null && !user.getMobile().equals(mobile);
    }

    public boolean dniRepeated(UserDto userDto) {
        return this.dniRepeated(userDto.getMobile(), userDto);
    }

    public boolean emailRepeated(String mobile, UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            return false;
        }
        User user = this.userRepository.findByEmail(userDto.getEmail());
        return userDto.getEmail() != null && user != null && !user.getMobile().equals(mobile);
    }

    public boolean emailRepeated(UserDto userDto) {
        return this.emailRepeated(userDto.getMobile(), userDto);
    }

    public boolean existsMobile(String mobile) {
        return this.userRepository.findByMobile(mobile) != null;
    }

    public boolean isAdmin(String mobile) {
        return isRole(mobile, Role.ADMIN);
    }

    public boolean isManager(String mobile) {
        return isRole(mobile, Role.MANAGER);
    }

    public boolean isOperator(String mobile) {
        return isRole(mobile, Role.OPERATOR);
    }

    public boolean isCustormer(String mobile) {
        return isRole(mobile, Role.CUSTOMER);
    }

    public boolean isRole(String mobile, Role role) {
        User user = this.userRepository.findByMobile(mobile);
        boolean maybeRole = false;
        Role[] roles = new Role[user.getRoles().length];
        roles = user.getRoles();
        for (int i = 0; i < roles.length; i++) {
            if (roles[i] == role) {
                maybeRole = true;
            }
        }
        return maybeRole;
    }

    public boolean putUser(String mobile, UserDto userDto, Role[] roles) {
        User user = this.userRepository.findByMobile(mobile);
        assert user != null;
        if (Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
            user.setMobile(userDto.getMobile());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setDni(userDto.getDni());
            user.setPassword(userDto.getPassword());
            user.setAddress(userDto.getAddress());
            user.setActive(userDto.isActive());
            user.setRoles(userDto.getRoles());
            this.userRepository.save(user);
        } else {
            return false;
        }
        return true;
    }

    public boolean deleteUser(String mobile, Role[] roles) {
        User userBd = this.userRepository.findByMobile(mobile);
        if (userBd == null) {
            return true;
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(userBd.getRoles()))) {
            this.userRepository.delete(userBd);
            return true;
        } else {
            return false;
        }
    }

    public Optional<UserDto> readUser(String mobile, Role[] roles) {
        User userBd = this.userRepository.findByMobile(mobile);
        if (userBd == null) {
            return Optional.empty();
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(userBd.getRoles()))) {
            return Optional.of(new UserDto(userBd));
        } else {
            return Optional.empty();
        }
    }

    public List<UserMinimumDto> readCustomerAll() {
        return this.userRepository.findCustomerAll();
    }

    public UserMinimumDto username(String mobile) {
        User user = this.userRepository.findByMobile(mobile);
        return new UserMinimumDto(user.getMobile(), user.getUsername());
    }

    public Optional<UserDto> readUser(String mobile, String mobileLogged) {
        User userLogged = this.userRepository.findByMobile(mobileLogged);
        return this.readUser(mobile, userLogged.getRoles());
    }

    public boolean putUser(String mobile, UserDto userDto, String mobileLogged) {
        User userLogged = this.userRepository.findByMobile(mobileLogged);
        return this.putUser(mobile, userDto, userLogged.getRoles());
    }

    public List<UserMinimumDto> find(String mobile, String username, String dni, String address) {
        return this.userRepository.findCustomerAll();
        
    }

}
