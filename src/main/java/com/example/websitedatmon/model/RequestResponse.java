package com.example.websitedatmon.model;

import com.example.websitedatmon.entity.Food;
import com.example.websitedatmon.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestResponse {
    public Integer id;
    public String FoodName;
    public String reason;
    public String image;
    public String username;
    public Integer status;
    public Food food;
    public Orders order;

}
