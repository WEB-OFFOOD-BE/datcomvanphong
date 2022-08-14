package com.example.websitedatmon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Menu {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    private String date;
    @Column(name = "status")
    private Integer status;
    @Column(name = "food_id")
    private Integer foodId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "isActive")
    private Byte isActive;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id",insertable = false,updatable = false)
    private Food food;
}
