package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryResponse {
    Orders order;
    Boolean isToday;
}
