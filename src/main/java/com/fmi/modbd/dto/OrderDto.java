package com.fmi.modbd.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderDto {

    private Long id;

    private Integer quantity;

    private BigDecimal totalOrderPrice;

    private OrderDateDto orderDate;

    private PromoCodeDto promoCode;

    private DriverDto driver;

    private MenuDto menu;

    private RestaurantDto restaurant;

    private CustomerDto customer;
}
