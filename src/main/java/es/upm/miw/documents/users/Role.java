package es.upm.miw.documents.users;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum Role {
    ADMIN, MANAGER, OPERATOR, CUSTOMER, ANONYMOUS, AUTHENTICATED;

    public String roleName() {
        return "ROLE_" + this.toString();
    }

}
