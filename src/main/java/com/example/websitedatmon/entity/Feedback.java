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
public class Feedback {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "title")
    private String title;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "isActive")
    private Byte isActive;
    @Column(name = "food_id")
    private Integer foodId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false,updatable = false)
    private User user;
}
