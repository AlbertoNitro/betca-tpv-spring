package es.upm.miw.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.User;

@JsonInclude(Include.NON_NULL)
public class UserDto extends UserMinimumDto {

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
        super(mobile, username);
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
        super(user.getMobile(), user.getUsername());
        this.email = user.getEmail();
        this.dni = user.getDni();
        this.address = user.getAddress();
        this.active = user.isActive();
        this.registrationDate = user.getRegistrationDate();
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
        return "[" + super.toString() + "UserDto [password=" + password + ", email=" + email + ", dni=" + dni + ", address=" + address
                + ", active=" + active + ", registrationDate=" + registrationDate + "] ]";
    }

}
