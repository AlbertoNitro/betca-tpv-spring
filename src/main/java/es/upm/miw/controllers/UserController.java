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

    public boolean dniRepeated(UserDto userDto) {
        User user = this.userRepository.findByDni(userDto.getDni());
        return userDto.getDni() != null && user != null && !user.getMobile().equals(userDto.getMobile());
    }

    public boolean emailRepeated(UserDto userDto) {
        User user = this.userRepository.findByEmail(userDto.getEmail());
        return userDto.getEmail() != null &&  user != null && !user.getMobile().equals(userDto.getMobile());
    }

    public boolean existsMobile(String mobile) {
        return this.userRepository.findByMobile(mobile) != null;
    }

    public boolean putUser(UserDto userDto, Role[] roles) {
        User user = this.userRepository.findByMobile(userDto.getMobile());
        assert user != null;
        if (Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setDni(userDto.getDni());
            user.setAddress(userDto.getAddress());
            user.setActive(userDto.isActive());
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

}
