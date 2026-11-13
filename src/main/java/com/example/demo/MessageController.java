package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private List<String> userMessages = new ArrayList<>();
    private static final int MAX_MESSAGES = 5;

    // http://localhost:8080
    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    // http://localhost:8080/messages
    @GetMapping("/messages")
    public List<String> getAllMessages() {
        return userMessages;
    }

    @PostMapping("/messages")
    public String publishMessage(@RequestBody(required = false) String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Error: Message cannot be empty!";
        }
        if (userMessages.size() >= MAX_MESSAGES) {
            userMessages.remove(0);
        }
        userMessages.add(message);
        return "Message published successfully!";
    }

    @PutMapping("/messages/{index}")
    public String updateMessage(@PathVariable int index, @RequestBody String message) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.set(index, message);
            return "Message updated successfully!";
        }
        return "Message not found at index " + index;
    }

    @DeleteMapping("/messages/{index}")
    public String deleteMessage(@PathVariable int index) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.remove(index);
            return "Message deleted successfully!";
        }
        return "Message not found at index " + index;
    }

    @DeleteMapping("/messages/clear")
    public String clearAllMessages() {
        userMessages.clear();
        return "All messages cleared successfully!";
    }

    @GetMapping("/messages/{index}")
    public String getMessageByIndex(@PathVariable int index) {
        if (index >= 0 && index < userMessages.size()) {
            return userMessages.get(index);
        }
        return "Message not found at index " + index;
    }

    @GetMapping("/messages/count")
    public int countMessages() {
        return userMessages.size();
    }
}