package es.upm.miw.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import es.upm.miw.documents.core.User;

public class UserDto {

    private static final String NINE_DIGITS = "\\d{9}";

    @NotNull
    @Pattern(regexp = NINE_DIGITS)
    private String mobile;

    @NotNull
    private String username;

    private String password;

    private String email;

    private String dni;

    private String address;

    private Boolean active;

    private Date registrationDate;

    public UserDto() {
        // Empty for framework
    }

    public UserDto(String mobile, String username, String password, String email, String dni, String address, Boolean active) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.email = email;
        this.setDni(dni);
        this.address = address;
        this.active = active;
    }

    public UserDto(String mobileNamePass) {
        this(mobileNamePass, "name" + mobileNamePass, "pass" + mobileNamePass, null, null, null, null);
    }

    public UserDto(User user) {
        this.mobile = String.valueOf(user.getMobile());
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.dni = user.getDni();
        this.address = user.getAddress();
        this.active = user.isActive();
        this.registrationDate = user.getRegistrationDate();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
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
        if (email != null) {
            this.email = email.toLowerCase();
        } else {
            this.email = email;
        }
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni != null) {
            this.dni = dni.toUpperCase();
        } else {
            this.dni = dni;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
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
