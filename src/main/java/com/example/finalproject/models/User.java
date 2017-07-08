package com.example.finalproject.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull; //try syncing the external library manually
import javax.validation.constraints.Size; //if this is not appearing. Also turn off other projects//

/**
 * Created by Natalie on 7/2/2017.
 */
@Entity //this is an entity in the database (I think)//
public class User {

    @Id
    @GeneratedValue
    private int userId;

    @NotNull
    @Size(min=3, max=20)
    private String username;

    @NotNull
    @Size(min=8, max=18)
    private String password;

    @NotNull
    private String verify;

    @Email
    private String email;

    public User() {
    }

    public User(int userId, String username, String password, String verify, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.verify = verify;
        this.email = email;
    }

    public int getUserId() {
        return userId;
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

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //include a verification method in this class?
}
