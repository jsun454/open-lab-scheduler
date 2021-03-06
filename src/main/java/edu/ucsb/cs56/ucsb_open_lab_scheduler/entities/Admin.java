package edu.ucsb.cs56.ucsb_open_lab_scheduler.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String email;

    private boolean isPermanentAdmin = false;

    public Admin() {
    }

    public Admin(String email) {
        this.email = email;
    }

    public Admin(String email, boolean status) {
        this.email = email;
        this.isPermanentAdmin = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsPermanentAdmin() {
        return isPermanentAdmin;
    }

    public void setIsPermanentAdmin(boolean status) {
        isPermanentAdmin = status;
    }

}