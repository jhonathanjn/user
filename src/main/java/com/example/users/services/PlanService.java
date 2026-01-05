package com.example.users.services;

import com.example.users.dto.PlanDto;
import com.example.users.dto.UserDto;
import com.example.users.entitys.Plan;
import com.example.users.entitys.User;
import com.example.users.repository.PlanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public List<Plan> getAllPlans(){
        List<Plan> plans = planRepository.findAll();
        return plans;
    }

    public Plan getId(Long id){
        return planRepository.findById(id).orElse(null);
    }

    public Plan createPlan(PlanDto dto){
        Plan newPlan = new Plan();
        newPlan.setName(dto.name());
        newPlan.setDuration(dto.duration());
        newPlan.setDescriptions(dto.descriptions());
        newPlan.setPrice(dto.price());
        newPlan.setActive(true);
        newPlan.setCreateAt(LocalDateTime.now());

        if (dto.price() == null){
            newPlan.setPrice(BigDecimal.valueOf(0));
        }

        return planRepository.save(newPlan);
    }

    public ResponseEntity updatePlans(Long id, PlanDto dto){
        Plan plan = planRepository.findById(id).orElse(null);

        plan.setName(dto.name());
        plan.setDuration(dto.duration());
        plan.setDescriptions(dto.descriptions());
        plan.setPrice(dto.price());

        if (plan == null)
            return ResponseEntity.status(HttpStatus.OK).body("Usuario não encontrado!");

        return ResponseEntity.status(HttpStatus.OK).body(planRepository.save(plan));
    }

    public ResponseEntity deleteId(Long id){
        Optional<Plan> planDeleted = planRepository.findById(id);

        if (planDeleted.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado!");

        planRepository.delete(planDeleted.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deletado");
    }


}
