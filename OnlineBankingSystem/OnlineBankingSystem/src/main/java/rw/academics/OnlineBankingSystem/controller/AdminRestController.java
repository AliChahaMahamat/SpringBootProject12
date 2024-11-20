package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.academics.OnlineBankingSystem.model.MyAppUser;
import rw.academics.OnlineBankingSystem.model.MyAppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private MyAppUserRepository userRepository;

    @GetMapping("/users")
    public List<MyAppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
