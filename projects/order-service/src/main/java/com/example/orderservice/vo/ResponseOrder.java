package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private String quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private String createAt;
    private String orderId;
}
