package by.kulevets.demociproj.controller;

import by.kulevets.demociproj.entity.pojo.PostPojo;
import by.kulevets.demociproj.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class PostController {

    @Qualifier("defaultPostService")
    private final PostService service;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("posts", service.getAll());
        return "index";
    }

    @GetMapping("create-post")
    public String postCreate(Model model) {
        model.addAttribute("pojo", new PostPojo());
        return "create-post";
    }

    @PostMapping("create-post")
    public String postCreatePost(@ModelAttribute("pojo") PostPojo pojo) {
        service.create(pojo);
        return "redirect:/";
    }

}
