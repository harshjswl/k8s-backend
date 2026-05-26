package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // For development convenience
public class ApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "connected");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = getPredefinedAnswer(question);
        Map<String, String> response = new HashMap<>();
        response.put("question", question);
        response.put("answer", answer);
        return ResponseEntity.ok(response);
    }

    private String getPredefinedAnswer(String question) {
        switch (question.toLowerCase()) {
            case "what is your name?":
                return "I am Nexus AI, your futuristic chatbot assistant.";
            case "how are you?":
                return "I'm functioning optimally, thank you for asking!";
            case "what can you do?":
                return "I can answer predefined questions, provide information, and engage in conversations.";
            case "tell me a joke":
                return "Why did the computer go to the doctor? Because it had a virus!";
            case "what is the meaning of life?":
                return "42, according to Douglas Adams.";
            case "how does ai work?":
                return "AI works by processing data, learning patterns, and making decisions based on algorithms.";
            default:
                return "I'm sorry, I don't have a predefined answer for that question. Try asking one of the available questions!";
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
