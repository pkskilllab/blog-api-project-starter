package com.embarkx.blogapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    private static List<String> posts = new ArrayList<>();

    @Value("${blog.post.max-title-length}")
    private int maxTitleLength;

    @Value("${blog.post.max-content-length}")
    private int maxContentLength;

    @Value("${blog.post.validate-max-length}")
    private int validateMaxLength;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDTO request) {
        String title = request.getTitle();
        String content = request.getContent();
        if (title == null || title.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must not be empty");
        }
        if (content == null || content.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content must not be empty");
        }
        if (title.length() > maxTitleLength) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title must not exceed " + maxTitleLength + " characters");
        }
        if (content.length() > maxContentLength) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content must not exceed " + maxContentLength + " characters");
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
    public ResponseEntity<String> getPost(@PathVariable int id) {
        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        return ResponseEntity.ok(posts.get(id));
    }

    @PostMapping("/validate")
    public String validateContent(@RequestParam String content) {
        if (content.length() > validateMaxLength) {
            return "Too long";
        }
        return "OK";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        if (id < 0 || id >= posts.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        posts.remove(id);
        return ResponseEntity.ok("Deleted");
    }

@GetMapping("/total")
public String getTotalWordCount() {
    List<String> wordCounts = List.of("100", "200", "300");
    int total = 0;
    for (String count : wordCounts) {
        total += Integer.parseInt(count);
    }
    return "Total words: " + total;
}
}