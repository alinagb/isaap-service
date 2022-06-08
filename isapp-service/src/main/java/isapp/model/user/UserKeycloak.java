package isapp.model.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserKeycloak {

    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String password;

    public UserKeycloak() {
    }

    public UserKeycloak(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
