package com.example.users.services;

import com.example.users.dto.UserDto;
import com.example.users.entitys.User;
import com.example.users.entitys.UserRoles;
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
        newUser.setInitTime(LocalDateTime.now());

        if (newUser.getRoles() == UserRoles.GRATIS){
            newUser.setEndTime(LocalDateTime.now().plusDays(7));
        }else if (newUser.getRoles() == UserRoles.PAGO){
            newUser.setEndTime(LocalDateTime.now().plusDays(30));
        }else {
            newUser.setRoles(UserRoles.GRATIS);
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

    public User updatePlan(Long id){
        User upgrade = userRepository.findById(id).orElse(null);

        upgrade.setRoles(UserRoles.PAGO);
        upgrade.setInitTime(LocalDateTime.now());
        upgrade.setEndTime(LocalDateTime.now().plusMonths(1));

        return userRepository.save(upgrade);

    }

}
