package com.example.websitedatmon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "created")
    private String created;
    @Column(name = "status")
    private Integer status;
    @Column(name = "food_id")
    private Integer foodId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "isActive")
    private Byte isActive;
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "orders")
    private List<Request> requests;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false, insertable = false,updatable = false)
    private Food food;

}
