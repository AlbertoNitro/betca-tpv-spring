package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.repositories.core.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public Optional<String> createUser(UserDto userDto, Role[] roles) {
        if (this.userRepository.findByMobile(userDto.getMobile()) != null) {
            return Optional.of("Mobile ya existente");
        }
        User user = new User(userDto.getMobile(), userDto.getUsername(), userDto.getPassword(), userDto.getDni(), userDto.getAddress(),
                userDto.getEmail());
        user.setRoles(roles);
        this.userRepository.save(user);
        return Optional.empty();
    }

    public Optional<String> putUser(UserDto userDto, Role[] roles) {
        User user = this.userRepository.findByMobile(userDto.getMobile());
        if (user == null) {
            return Optional.of("Mobile no existente");
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setDni(userDto.getDni());
            user.setAddress(userDto.getAddress());
            user.setActive(userDto.isActive());
            this.userRepository.save(user);
        } else {
            return Optional.of("No se tiene el rol suficiente para actualizar al usr");
        }
        return Optional.empty();
    }

    public Optional<String> deleteUser(long mobile, Role[] roles) {
        User userBd = this.userRepository.findByMobile(mobile);
        if (userBd == null) {
            return Optional.empty();
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(userBd.getRoles()))) {
            this.userRepository.delete(userBd);
            return Optional.empty();
        } else {
            return Optional.of("No se tiene el rol suficiente para borrar al usr");
        }
    }

    public Optional<UserDto> readUser(long mobile, Role[] roles) {
        User userBd = this.userRepository.findByMobile(mobile);
        if (userBd == null) {
            return Optional.empty();
        } else if (Arrays.asList(roles).containsAll(Arrays.asList(userBd.getRoles()))) {
            return Optional.of(new UserDto(userBd));
        } else {
            return Optional.empty();
        }
    }

    public List<UserDto> readAll(Role[] roles) {
        List<User> userList = this.userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            if (Arrays.asList(roles).containsAll(Arrays.asList(user.getRoles()))) {
                userDtoList.add(new UserDto(user));                
            }
        }
        return userDtoList;
    }

}
