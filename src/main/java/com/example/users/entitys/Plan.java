package com.example.users.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "db_plans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer duration;

    private String descriptions;

    @Column(nullable = false)
    private BigDecimal price;

    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createAt = LocalDateTime.now();

}
