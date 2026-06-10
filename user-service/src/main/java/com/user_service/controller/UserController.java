package com.user_service.controller;

import com.user_service.dto.LoginRequestDTO;
import com.user_service.dto.LoginResponseDTO;
import com.user_service.dto.UserRequestDTO;
import com.user_service.dto.UserResponseDTO;
import com.user_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO){
        return new ResponseEntity<>(userService.registerUser(requestDTO), HttpStatusCode.valueOf(HttpStatus.SC_CREATED));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserBYId(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

   @GetMapping("/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
   }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>>  getAllUser(@PathVariable Long id , @Valid @RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.ok(userService.getAllUsers());
   }

   @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO
                                                      ){
        return ResponseEntity.ok(userService.updateUser(id , requestDTO));

   }

   @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
   }


}
