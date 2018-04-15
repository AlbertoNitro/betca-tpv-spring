package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TokenController;
import es.upm.miw.controllers.UserController;
import es.upm.miw.dtos.TokenOutputDto;
import es.upm.miw.dtos.UserMinimumDto;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TokenResource.TOKENS)
public class TokenResource {

    public static final String TOKENS = "/tokens";

    public static final String AUTHENTICATED = "/authenticated";
    
    public static final String USERNAME = "/username";

    @Autowired
    private TokenController tokenController;
    
    @Autowired
    private UserController userController;


    @PreAuthorize("authenticated")
    @PostMapping
    public TokenOutputDto login(@AuthenticationPrincipal User activeUser) {
        return tokenController.login(activeUser.getUsername());
    }

    @GetMapping(value = USERNAME)
    public UserMinimumDto username(@AuthenticationPrincipal User activeUser) {
        return this.userController.username(activeUser.getUsername());
    }
    

}
