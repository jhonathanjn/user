package com.example.users.controllers;

import com.example.users.dto.UserDto;
import com.example.users.entitys.User;
import com.example.users.repository.UserRepository;
import com.example.users.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    //Lista todos os usuarios do Banco de Dados
    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    //Busca de usuarios do Banco por ID
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Long id){
        User userId = userService.getId(id);
        //verificação se o usuario existe, caso não exista envia menssagem de erro
        if (userId == null){
            return ResponseEntity.ok(Map.of("menssage", "User não encontrado"));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    //Criar usuarios
    @PostMapping("/create")
    public ResponseEntity<User> newUser(@RequestBody UserDto dto){
        User saveUser = userService.createUser(dto); // Recebe o user dto(nome, email e password) e cria o user no banco
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser); // Retorna a função save user, concluido a ação

    }

    //Deletar user por ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id){
        return userService.deleteId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, dto));
    }

    @PutMapping("plan/{id}")
    public ResponseEntity updatePlan(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePlan(id));
    }
}
