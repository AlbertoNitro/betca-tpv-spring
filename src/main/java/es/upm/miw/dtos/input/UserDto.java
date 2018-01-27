package es.upm.miw.dtos.input;

public class UserDto {

    private long mobile;

    private String username;

    private String password;

    private String email;

    private String dni;

    private String address;

    public UserDto() {
    }

    public UserDto(long mobile, String username, String password, String email, String dni, String address) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dni = dni;
        this.address = address;
    }

    public UserDto(long mobile) {
        this(mobile, "name" + mobile, "pass" + mobile, null, null, null);
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

    @Override
    public String toString() {
        return "UserDto [mobile=" + mobile + ", username=" + username + ", password=" + password + ", email=" + email + ", dni=" + dni
                + ", address=" + address + "]";
    }

}
