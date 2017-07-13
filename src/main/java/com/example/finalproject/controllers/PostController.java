package com.example.finalproject.controllers;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

//Tiny MCE password = Mc67901!!! //

@Controller
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPostForm(Model model) {

        model.addAttribute("title", "Make a new post");
        model.addAttribute("post", new Post());
        return "post/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPostForm(@ModelAttribute @Valid Post newPost,
                                     Errors errors, Model model) {

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Posted!");
        postDao.save(newPost);

        return "post/index";

    }
    @RequestMapping(value = "edit/{postId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int postId) {
        model.addAttribute("post", postDao.findOne(postId));
        return "post/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String processEditForm(int postId, String title, String headline, String textBody) {
        Post post = postDao.findOne(postId);
        post.setHeadline(headline);
        post.setTextBody(textBody);
        post.setTitle(title);

        return "post/index";
    }
}