package com.fmi.modbd.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RestaurantDto {

    private Long id;

    private String description;

    private String name;

    private CategoryDto category;

    private List<MenuDto> menus = new ArrayList<>();

    private Set<OrderDto> orders;

}
