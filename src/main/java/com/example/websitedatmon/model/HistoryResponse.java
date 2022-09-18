package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HistoryResponse {
    public Orders order;
    public Boolean isToday;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Boolean getIsToday() {
        return isToday;
    }

    public void setToday(Boolean today) {
        isToday = today;
    }
}
