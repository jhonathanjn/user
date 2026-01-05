package com.example.users.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record PlanDto(String name, Integer duration, String descriptions, BigDecimal price) {
}
