package com.example.websitedatmon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "title")
    private String title;
    @Column(name = "isActive")
    private Byte isActive;
}
