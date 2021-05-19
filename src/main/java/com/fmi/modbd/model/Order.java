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
@SecondaryTable(name = "ORDERS_ALL")
@SecondaryTable(name = "ORDERS_BUCURESTI_ALL")
@SecondaryTable(name = "ORDERS_BRASOV_ALL")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "promo_id")
    private PromoCode promoCode;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Driver driver;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Menu menu;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Restaurant restaurant;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Customer customer;
}
