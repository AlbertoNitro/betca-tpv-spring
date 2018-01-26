package es.upm.miw.documents.users;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.assertj.core.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document
public class User {

    @Id
    private long mobile;

    private String username;

    private String password;

    private boolean active;

    private String email;

    private String dni;

    private String address;

    private Date registrationDate;

    private Role[] roles;

    private Token token;

    public User() {
    }

    public User(long mobile, String username, String password, String dni, String address, String email, boolean active) {
        this.mobile = mobile;
        this.username = username;
        this.dni = dni;
        this.address = address;
        this.email = email;
        this.setPassword(password);
        this.registrationDate = new Date();
        this.active = active;
        this.roles = Arrays.array(Role.CUSTOMER);
    }

    public User(long mobile, String username, String password) {
        this(mobile, username, password, "", "", "", true);
    }

    public long getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (mobile ^ (mobile >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return mobile == ((User) obj).mobile;
    }

    @Override
    public String toString() {
        String date = "null";
        if (registrationDate != null) {
            date = new SimpleDateFormat("dd-MMM-yyyy ").format(registrationDate.getTime());
        }
        return "User [mobile=" + mobile + ", username=" + username + ", password=" + password + ", active=" + active + ", email=" + email
                + ", dni=" + dni + ", address=" + address + ", registrationDate=" + date + ", roles=" + java.util.Arrays.toString(roles)
                + ", token=" + token + "]";
    }

}
