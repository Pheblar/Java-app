package by.kulevets.demociproj.controller;

import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import by.kulevets.demociproj.service.PostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class ApiPostController {

    @Qualifier("defaultPostService")
    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostModel>> getAll() {
        var posts = postService.getAll();
        return ResponseEntity
                .ok(posts);
    }

    @PostMapping(value = "create-post", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> postCreatePost(@RequestBody PostPojo pojo) {
        postService.create(pojo);
        return ResponseEntity.ok("Created!");
    }
}
