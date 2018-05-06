package es.upm.miw.dtos;

import java.util.Arrays;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;

public class TokenOutputDto {

    private String token;

    private Role[] roles;
    
    private long creationDate;

	public TokenOutputDto() {
        // Empty for framework
    }

    public TokenOutputDto(User user) {
        this.token = user.getToken().getValue();
        this.roles = user.getRoles();
        this.creationDate = user.getToken().getCreationDate().getTime();
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
    
    public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

    @Override
    public String toString() {
        return "TokenDto [token=" + token + ", roles=" + Arrays.toString(roles) + "]";
    }
}
