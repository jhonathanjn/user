package com.example.users.services;

import com.example.users.dto.UserDto;
import com.example.users.entitys.Plan;
import com.example.users.entitys.User;
import com.example.users.entitys.UserRoles;
import com.example.users.repository.PlanRepository;
import com.example.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    public List<User> getAll(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getId(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(UserDto dto){
        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);

        if (newUser.getRoles() == null){
            newUser.setRoles(UserRoles.USER);
        }

        newUser.setCreatedAt(LocalDateTime.now());

        Plan freePlan = planRepository.findByName("FREE")
                .orElseThrow(() -> new RuntimeException("Plano Free não encontrado!"));

        newUser.setPlan(freePlan);

        LocalDateTime start = LocalDateTime.now();
        newUser.setPlanStart(start);

        if (freePlan.getDuration() != null && freePlan.getDuration() > 0) {
            newUser.setPlanEnd(start.plusDays(freePlan.getDuration()));
        } else {
            // plano vitalício (FREE eterno, por exemplo)
            newUser.setPlanEnd(null);
        }


        return userRepository.save(newUser);

    }

    public User updateUser(Long id, UserDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        return userRepository.save(user);
    }

    public ResponseEntity deleteId(Long id){
        Optional<User> userDeleted = userRepository.findById(id);

        if (userDeleted.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse usúario não existe!");

        userRepository.delete(userDeleted.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deletado");
    }

    public ResponseEntity updatePlan(Long idUser, Long idPlan){
        User upgradePlan = userRepository.findById(idUser).orElse(null);
        Plan setPlan = planRepository.findById(idPlan).orElse(null);


        if (upgradePlan == null || setPlan == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro, usuario ou plano não existem!");

        } else if ( upgradePlan.getPlan() != null &&
                    upgradePlan.getPlan().getId().equals(setPlan.getId()) &&
                    upgradePlan.getPlanEnd() != null &&
                    upgradePlan.getPlanEnd().isAfter(LocalDateTime.now())) {

            return ResponseEntity.status(HttpStatus.OK).body("O Plano ja esta ativo");
        } else {
            upgradePlan.setPlan(setPlan);

            LocalDateTime start = LocalDateTime.now();
            upgradePlan.setPlanStart(start);

            upgradePlan.setPlanEnd(start.plusDays(setPlan.getDuration()));
        }



        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(upgradePlan));

    }

}
