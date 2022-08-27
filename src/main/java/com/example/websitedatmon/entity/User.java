package com.example.websitedatmon.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user")
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "image")
    private String image;
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "isActive")
    private Integer isActive;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false,updatable = false)
    private Role role;

    public List<Orders> getOrders() {
        return orders;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public Role getRole() {
        return role;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getImage() {
        return image;
    }

    public int getRoleId() {
        return roleId;
    }

    public Integer getIsActive() {
        return isActive;
    }
}
