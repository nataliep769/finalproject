package com.example.finalproject.controllers;

import com.example.finalproject.models.User;
import com.example.finalproject.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Natalie on 7/1/2017.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index() {
        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddUserForm(Model model) {

        model.addAttribute("title", "New User Form");
        model.addAttribute("user", new User());
        return "user/add";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User newUser,
                                       Errors errors, Model model, String verify) {

        if (errors.hasErrors()) {
            return "user/add";
        }

        boolean passwordsMatch = true;

        if (newUser.getPassword() == null || verify == null
                || !newUser.getPassword().equals(verify)) {
            passwordsMatch = false;
            model.addAttribute("verifyError", "Passwords must match");
        }

        if (passwordsMatch) {
            userDao.save(newUser);
            return "user/index";
        }

        model.addAttribute("title", "New User Form");
        return "user/add";
        //check to make sure all of the libraries in other files are imported when reopening IntelliJ//
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginUserForm(Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST) //change the mapping value?//
    public String processLoginForm(@ModelAttribute @Valid User user, Errors errors, Model model) {
        model.addAttribute("title", "Login");

        //if (errors.hasErrors()) {
            //return "user/login";
        //}
        //Maybe ask someone about this part below?//
        //Do I need to get and set the User Id?//
        if (userDao.exists(user.getUserId())) { //need to define user//
            return "user/index";
          //  return //a page that welcomes the user, or send them to the home page of Between the Notes//
        } else {

        return "user/login";

        }
    }
}
