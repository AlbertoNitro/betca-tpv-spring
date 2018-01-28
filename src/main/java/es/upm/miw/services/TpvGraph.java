package es.upm.miw.services;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.documents.users.Token;
import es.upm.miw.documents.users.User;

public class TpvGraph {

    private List<User> userList;
    private List<Token> tokenList;
    
    public TpvGraph() {
        userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    @Override
    public String toString() {
        return "TpvGraph [userList=" + userList + ", tokenList=" + tokenList + "]";
    }

}
