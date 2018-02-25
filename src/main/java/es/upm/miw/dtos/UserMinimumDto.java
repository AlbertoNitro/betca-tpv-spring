package es.upm.miw.dtos;

public class UserMinimumDto {
    private String mobile;

    private String username;

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

    @Override
    public String toString() {
        return "UserMinimumDto [mobile=" + mobile + ", username=" + username + "]";
    }
}
