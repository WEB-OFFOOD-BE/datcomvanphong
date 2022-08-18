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
    String FoodName;
    String reason;
    String image;
    String username;
    String status;

}
