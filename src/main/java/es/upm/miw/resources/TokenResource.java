package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TokenController;
import es.upm.miw.dtos.output.TokenDto;

@RestController
@RequestMapping(TokenResource.TOKENS)
public class TokenResource {

    public static final String TOKENS = "/tokens";

    @Autowired
    private TokenController tokenController;

    @PreAuthorize("authenticated")
    @RequestMapping(method = RequestMethod.POST)
    public TokenDto login(@AuthenticationPrincipal User activeUser) {
        return tokenController.login(Long.parseLong(activeUser.getUsername()));
    }

}
