package com.example.websitedatmon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestResponse {
    public String FoodName;
    public String reason;
    public String image;
    public String username;
    public String status;

}
