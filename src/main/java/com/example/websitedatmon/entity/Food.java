package com.example.websitedatmon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "food")
@Builder
@Entity
public class Food {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    @Column(name = "id")
    private Integer id;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "isActive")
    private Integer isActive;

    @Column(name = "avg_rate")
    private Float avgRate;

    @OneToMany(mappedBy = "food")
    private List<Orders> orders;

    @OneToMany(mappedBy = "food")
    private List<Comment> comments;

    @OneToMany(mappedBy = "food")
    private List<Menu> menus;
}
