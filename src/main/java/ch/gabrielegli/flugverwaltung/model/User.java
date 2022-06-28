package ch.gabrielegli.flugverwaltung.model;

import java.util.UUID;

/**
 * class to create and save a user
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 28-06-22
 */
public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;


    /**
     * set the default guest user
     */
    public User() {
        this.userUUID = UUID.randomUUID().toString();
        this.username = "guest";
        this.password = null;
        this.role = "guest";
    }

    /**
     * getter
     *
     * @return
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * setter
     *
     * @param userUUID new uuid
     */
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * getter
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * setter
     *
     * @param role new role
     */
    public void setRole(String role) {
        this.role = role;
    }
}

