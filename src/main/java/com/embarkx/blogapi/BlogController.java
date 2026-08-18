package com.embarkx.blogapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    private static List<String> posts = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> createPost(@RequestParam String title, @RequestParam String content) {
        if (title == null || title.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must not be empty");
        }
        if (content == null || content.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content must not be empty");
        }
        if (title.length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must not exceed 50 characters");
        }
        if (content.length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content must not exceed 50 characters");
        }
        String post = title + ":" + content;
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created");
    }

    @GetMapping
    public List<String> getAllPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable int id) {
        return posts.get(id);
    }

    @PostMapping("/validate")
    public String validateContent(@RequestParam String content) {
        if (content.length() > 5000) {
            return "Too long";
        }
        return "OK";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable int id) {
        posts.remove(id);
        return "Deleted";
    }

@GetMapping("/total")
public String getTotalWordCount() {
    List<String> wordCounts = List.of("100", "200", "300");
    String total = "";
    for (String count : wordCounts) {
        total += count;
    }
    return "Total words: " + total;
}
}