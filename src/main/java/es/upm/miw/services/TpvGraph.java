package es.upm.miw.services;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.documents.users.User;

public class TpvGraph {

    private List<User> userList;
    
    public TpvGraph() {
        userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
