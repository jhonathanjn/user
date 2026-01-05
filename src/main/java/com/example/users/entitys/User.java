package com.example.users.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "plan_start")
    private LocalDateTime planStart = LocalDateTime.now();

    @Column(name = "plan_end")
    private LocalDateTime planEnd;

    @Enumerated(EnumType.STRING)
    private UserRoles roles = UserRoles.USER;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
