package com.example.finalproject.controllers;

import com.example.finalproject.models.Post;
import com.example.finalproject.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//Tiny MCE password = Mc67901!!! //

@Controller
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "")
    public String index(Model model) { //will need to add User in here//

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "All Posts");
        return "post/index";
    }

    @RequestMapping(value = "blog")
    public String blog(Model model) { //will need to add User in here//

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Between the Notes");
        return "post/blog";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPostForm(Model model) {

        //do I need to select the user from the database users? if user.getUsername == or .equals administrator//

        model.addAttribute("title", "Make a new post");
        model.addAttribute("post", new Post());
        return "post/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPostForm(@ModelAttribute @Valid Post newPost,
                                     Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Make a new post");
            model.addAttribute("textError", "Must enter text");
            return "post/add";
        }

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Posted!");
        postDao.save(newPost);

        return "redirect:";

    }
    @RequestMapping(value = "edit/{postId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int postId) {
        model.addAttribute("post", postDao.findOne(postId));
        model.addAttribute("title", "Edit Post");
        return "post/edit";
    }

    @RequestMapping(value = "edit/{postId}", method = RequestMethod.POST)
    public String processEditForm(@PathVariable int postId, Model model, String title, String headline, String textBody) {
        Post post = postDao.findOne(postId);
        post.setHeadline(headline);
        post.setTextBody(textBody);
        post.setTitle(title);
        postDao.save(post);
        model.addAttribute("post", post);
        //Add new post to the model?
        return "post/view";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Remove Post");
        return "post/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] postIds) {

        for (int postId : postIds) {
            postDao.delete(postId); //cascading delete needed?//
        }

        return "redirect:";
    }

    @RequestMapping(value = "view/{postId}", method = RequestMethod.GET)
    public String viewMenuForm(@PathVariable int postId, Model model) { //remember the importance of setting a path variable -- sets up the URL //
        Post post = postDao.findOne(postId);;

        model.addAttribute("title", post.getTitle());
        model.addAttribute("post", post);

        return "post/view";
    }

}