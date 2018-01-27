package es.upm.miw.controllers;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.users.Role;
import es.upm.miw.documents.users.User;
import es.upm.miw.dtos.input.UserDto;
import es.upm.miw.repositories.users.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public Optional<String> createUser(UserDto userDto, Role[] roles) {
        if (this.userRepository.findOne(userDto.getMobile()) != null) {
            return Optional.of("Mobile ya existente");
        }
        User user = new User(userDto.getMobile(), userDto.getUsername(), userDto.getPassword(), userDto.getDni(), userDto.getAddress(),
                userDto.getEmail());
        user.setRoles(roles);
        this.userRepository.save(user);
        return Optional.empty();
    }

    public Optional<String> deleteUser(long mobile, Role[] roles) {
        User userBd = this.userRepository.findOne(mobile);
        if (userBd == null) {
            return Optional.empty();
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(userBd.getRoles()))) {
            this.userRepository.delete(mobile);
            return Optional.empty();
        } else {
            return Optional.of("No se tiene el rol suficiente para borrar al usr");
        }
    }

}
