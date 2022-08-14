package com.example.websitedatmon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "food_id")
    private int foodId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "isActive")
    private Byte isActive;
    @Column(name = "rate")
    private Float rate;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id", insertable = false,updatable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false,updatable = false)
    private User user;

}
