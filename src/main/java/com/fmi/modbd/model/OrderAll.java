package com.fmi.modbd.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.modbd.model.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ORDERS_ALL")
public class OrderAll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_order_price", nullable = false)
    private BigDecimal totalOrderPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderDate orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promo_id")
    private PromoCode promoCode;

    @ManyToOne(fetch = FetchType.EAGER)
    private Driver driver;

    @ManyToOne(fetch = FetchType.EAGER)
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;
}
