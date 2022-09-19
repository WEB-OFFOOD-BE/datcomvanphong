package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryResponse {
    Orders order;
    Boolean isToday;
}
