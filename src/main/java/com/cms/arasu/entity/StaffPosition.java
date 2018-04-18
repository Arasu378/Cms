package com.cms.arasu.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "staff_position")
public class StaffPosition  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "role")
    private String role;

    @Column(name = "position")
    private String position;

    @Column(name = "created_at")
    private String createdOn;

    @Column(name = "modified_at")
    private String modifiedOn;

    public StaffPosition() {
    }

    public StaffPosition(Long staffId, String role, String position, String createdOn, String modifiedOn) {
        this.staffId = staffId;
        this.role = role;
        this.position = position;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
