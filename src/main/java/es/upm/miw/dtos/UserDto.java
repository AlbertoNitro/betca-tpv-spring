package es.upm.miw.dtos;

import java.util.Date;

import es.upm.miw.documents.core.User;

public class UserDto {

    private long mobile;

    private String username;

    private String password;

    private String email;

    private String dni;

    private String address;

    private boolean active;

    private Date registrationDate;

    public UserDto() {
    }

    public UserDto(long mobile, String username, String password, String email, String dni, String address, boolean active) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dni = dni;
        this.address = address;
        this.active = active;
    }

    public UserDto(long mobile) {
        this(mobile, "name" + mobile, "pass" + mobile, null, null, null, true);
    }

    public UserDto(User user) {
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.dni = user.getDni();
        this.address = user.getAddress();
        this.active = user.isActive();
        this.registrationDate=user.getRegistrationDate();
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
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
        this.password = password;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "UserDto [mobile=" + mobile + ", username=" + username + ", password=" + password + ", email=" + email + ", dni=" + dni
                + ", address=" + address + ", active=" + active + ", registrationDate=" + registrationDate + "]";
    }

}
