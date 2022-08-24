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
@Table(name = "late_order")
public class LateOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "food_id")
    private int foodId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "is_active")
    private Integer isActive;
}
