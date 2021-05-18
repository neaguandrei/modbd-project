package com.fmi.modbd.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Orders")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_order_price", nullable = false)
    private BigDecimal totalOrderPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderDate orderDate;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private PromoCode promoCode;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Menu menu;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Customer customer;
}
