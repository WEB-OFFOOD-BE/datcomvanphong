package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Orders;
import com.example.websitedatmon.entity.User;
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
    User user;
    LocalDate orderDate;
    Food food;
}
