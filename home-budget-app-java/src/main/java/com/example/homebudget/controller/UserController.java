package com.example.homebudget.controller;

import com.example.homebudget.model.ApplicationUser;
import com.example.homebudget.repository.ApplicationUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final ApplicationUserRepository repository;

    public UserController(ApplicationUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ApplicationUser> list() {
        return repository.findAll();
    }

    @PostMapping
    public ApplicationUser create(@RequestBody ApplicationUser user) {
        return repository.save(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ApplicationUser> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        return repository.findById(id)
                .map(user -> {
                    user.setPassword(newPassword);
                    return ResponseEntity.ok(repository.save(user));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
