package com.programmingtechie.productservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

}
