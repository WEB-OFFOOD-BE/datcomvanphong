package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInfo {
    String name;
    String phoneNumber;
    LocalDate orderDate;
    Orders order;
}
