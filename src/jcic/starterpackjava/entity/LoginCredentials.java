package jcic.starterpackjava.entity;

/**
 * LoginCredentials contains the data required to login an account. Handles
 * sensitive information. It is used in post requests from the client, but is
 * never be returned to the client.
 *
 * @author dion
 */
public class LoginCredentials {

    private String email;
    private String password;

    public LoginCredentials() { }
    
    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
