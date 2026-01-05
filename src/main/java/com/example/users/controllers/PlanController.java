package com.example.users.controllers;

import com.example.users.dto.PlanDto;
import com.example.users.dto.UserDto;
import com.example.users.entitys.Plan;
import com.example.users.entitys.User;
import com.example.users.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(planService.getAllPlans());
    }

    @PostMapping("/create")
    public ResponseEntity creatPlan(@RequestBody PlanDto dto){
        Plan savePlan = planService.createPlan(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savePlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Long id){
        Plan planId = planService.getId(id);
        if (planId == null){
            return ResponseEntity.ok(Map.of("menssage", "User não encontrado"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(planId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlan(@PathVariable(value = "id") Long id){
        return planService.deleteId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePlan(@PathVariable(value = "id") Long id, @RequestBody PlanDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(planService.updatePlans(id, dto));
    }
}
