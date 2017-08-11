package com.example.finalproject.controllers;

import com.example.finalproject.models.User;
import com.example.finalproject.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

//Make sure to kill other tasks in order to run the debugger //
/**
 * Created by Natalie on 7/1/2017.
 */
@Controller
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
                                     Errors errors, Model model, String verify, @CookieValue(name = "id", required = false) String userIdCurrent,
                                     HttpServletResponse response) {

        List<User> users = (List<User>) userDao.findAll();

        for (User dbUser : users) {
            if (newUser.getUsername().equals(dbUser.getUsername())) {
                model.addAttribute("title", "New User Form");
                model.addAttribute("usernameError", "That username is taken. Please choose another");
                return "user/add";
            }
        }


        if (errors.hasErrors()) {
            model.addAttribute("title", "New User Form");
            return "user/add";
        }

        boolean passwordsMatch = true;

        if (newUser.getPassword() == null || verify == null
                || !newUser.getPassword().equals(verify)) {
            passwordsMatch = false;
            model.addAttribute("verifyError", "Passwords must match");
        }

        if (passwordsMatch) {

            String password_hash = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            String username = newUser.getUsername();
            String verify_hash = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            String email = newUser.getEmail();
            boolean administrator = false;

            User hashedUser = new User(username, password_hash, verify_hash, email, administrator);

            userDao.save(hashedUser);

            int userId = hashedUser.getUserId();

            Cookie userIdCookie = new Cookie("id", Integer.toString(userId));

            response.addCookie(userIdCookie);

            model.addAttribute("title", "Between the Notes");
            return "post/blog"; //change this//
        }

        model.addAttribute("title", "New User Form");
        return "user/add";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLoginUserForm(@CookieValue(name = "id", required = false) String userIdCookie,
                                       Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLoginForm(@ModelAttribute @Valid User user, Errors errors, Model model, @CookieValue(name = "id", required = false) String userIdCurrent,
                                   HttpServletResponse response) {

        model.addAttribute("title", "Login");
        List<User> users = (List<User>) userDao.findAll();

        for (User dbUser : users) {
            if (user.getUsername().equals(dbUser.getUsername()) && BCrypt.checkpw(user.getPassword(), dbUser.getPassword())) {

                int userId = dbUser.getUserId();

                Cookie userIdCookie = new Cookie("id", Integer.toString(userId)); //the second value in the parentheses is the string userIdCurrent

                response.addCookie(userIdCookie);

                model.addAttribute("title", "Between the Notes");
                return "post/blog";

            } else {
                model.addAttribute("error", "Invalid username and/or password");
            }
        }
        return "user/login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logOutUser(HttpServletResponse response, Model model) {

        Cookie idCookie = new Cookie("id", "");
        idCookie.setMaxAge(0);
        response.addCookie(idCookie);

        model.addAttribute("title", "Logged out!");
        model.addAttribute("logOutConfirm", "You have been logged out successfully!");
        model.addAttribute("user", new User());

        return "user/login";
    }
}