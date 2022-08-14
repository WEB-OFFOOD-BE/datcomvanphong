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
@Table(name = "request")
public class Request {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "created")
    private LocalDate created;
    @Column(name = "description")
    private String description;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "image")
    private String image;
    @Column(name = "isActive")
    private Byte isActive;
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false,updatable = false)
    private Orders orders;
}
