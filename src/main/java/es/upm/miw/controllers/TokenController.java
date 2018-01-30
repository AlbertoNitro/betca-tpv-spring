package es.upm.miw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Token;
import es.upm.miw.documents.core.User;
import es.upm.miw.dtos.output.TokenDto;
import es.upm.miw.repositories.core.UserRepository;

@Controller
public class TokenController {

    @Autowired
    private UserRepository userRepository;

    public TokenDto login(long mobile) {
        User user = userRepository.findByMobile(mobile);
        assert user != null;
        user.setToken(new Token());
        userRepository.save(user);
        return new TokenDto(user);
    }

}
