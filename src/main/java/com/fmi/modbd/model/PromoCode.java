package com.fmi.modbd.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "Promo_Code")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String value;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "promoCode",
            orphanRemoval = true
    )
    private Set<Order> orders;

}
