package es.upm.miw.dtos.output;

import java.util.Arrays;

import es.upm.miw.documents.users.Role;
import es.upm.miw.documents.users.User;

public class TokenDto {

    private String token;

    private Role[] roles;

    public TokenDto() {
    }

    public TokenDto(User user) {
        this.token = user.getToken().getValue();
        this.roles = user.getRoles();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "TokenDto [token=" + token + ", roles=" + Arrays.toString(roles) + "]";
    }
}
