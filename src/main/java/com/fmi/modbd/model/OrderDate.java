package com.fmi.modbd.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "OrderDate")
public class OrderDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_created", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date created;

    @Column(name = "date_completed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date completed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}
