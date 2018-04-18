package com.cms.arasu.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "staff")
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name ="email")
    @NotNull
    private String email;

    @Column(name="password")
    @NotNull
    private String password;

    @Column(name = "is_verified")
    private Boolean Verified;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "modified_on")
    private String modifiedOn;

    public Staff() {
    }

    public Staff(String firstName, String lastName, String email, String password, Boolean verified, String createdOn, String modifiedOn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        Verified = verified;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Boolean getVerified() {
        return Verified;
    }

    public void setVerified(Boolean verified) {
        Verified = verified;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
