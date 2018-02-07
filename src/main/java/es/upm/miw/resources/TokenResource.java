package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TokenController;
import es.upm.miw.controllers.UserController;
import es.upm.miw.documents.core.Role;
import es.upm.miw.dtos.output.TokenDto;

@RestController
@RequestMapping(TokenResource.TOKENS)
public class TokenResource {

    public static final String TOKENS = "/tokens";

    public static final String AUTHENTICATED_ROLES = "/authenticated/roles";

    @Autowired
    private TokenController tokenController;

    @Autowired
    private UserController userController;

    @PreAuthorize("authenticated")
    @RequestMapping(method = RequestMethod.POST)
    public TokenDto login(@AuthenticationPrincipal User activeUser) {
        return tokenController.login(Long.parseLong(activeUser.getUsername()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR') or hasRole('CUSTOMER')")
    @RequestMapping(value = AUTHENTICATED_ROLES, method = RequestMethod.GET)
    public Role[] tokenRoles(@AuthenticationPrincipal User activeUser) {
        return userController.readUserAuthenticatedRoles(Long.parseLong(activeUser.getUsername()));
    }

}
