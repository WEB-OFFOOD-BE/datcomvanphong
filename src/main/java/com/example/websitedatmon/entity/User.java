package com.example.websitedatmon.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
