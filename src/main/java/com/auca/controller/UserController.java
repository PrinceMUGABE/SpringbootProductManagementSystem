package com.auca.controller;

import com.auca.modal.User;
import com.auca.service.EmailService;
import com.auca.serviceImplementation.UserNotFoundException;
import com.auca.serviceImplementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @GetMapping("/AdminDashboard")
    public String adminDashboard(){
        return "adminDashboard";
    }

    @PostMapping("/user/save")
    public String saveSignupPage(User user, Model model, RedirectAttributes ra){
        try {
            // Check if the username already exists
            if (userServiceImplementation.usernameExists(user.getUsername())) {
                ra.addFlashAttribute("message", "Username already exists. Please choose a different username.");
                return "redirect:/signup";
            }

            User savedUser = userServiceImplementation.saveUser(user);

            // Send confirmation email
            String subject = "User Saved Successfully";
            String text = "Dear " + user.getUsername() + ",\n\nYour Account has been saved successfully.";

            emailService.sendConfirmationEmail(user.getEmail(), subject, text);



            model.addAttribute("pageTitle","Signup Form");
            model.addAttribute("user", savedUser);
            ra.addFlashAttribute("message", "Account created successfully, you can now login");


        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "login";
    }


    @GetMapping("/user/login")
    public String showLoginForm(Model model){
        User user = new User();
        model.addAttribute("user", user);

        return "login";
    }

    @PostMapping("/user/login/save")
    public String userLogin(Model model, User user, RedirectAttributes ra){
        User savedUser = userServiceImplementation.findUserByUsername(user.getUsername());

        if (savedUser == null) {
            ra.addFlashAttribute("message", "User not found");
            return "redirect:/user/login";
        }

        if ("admin".equals(savedUser.getUsername())) {
            if (savedUser.getPassword().equals(user.getPassword())){
                return "redirect:/AdminDashboard";
            }else{
                ra.addFlashAttribute("message", "Wrong Password");
                return "redirect:/user/login";
            }

        } else if (savedUser.getUsername().equals(user.getUsername())){
            if (savedUser.getPassword().equals(user.getPassword())) {
                return "UserDashboard";
            } else {
                ra.addFlashAttribute("message", "Wrong password");
                return "redirect:/user/login";
            }
        }else {
            ra.addFlashAttribute("message", "User not found");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/user/list")
    public String displayUsers(Model model){
        List<User> listUser = userServiceImplementation.displayUsers();
        model.addAttribute("listUsers", listUser);

        return "usersDashboard";
    }




    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try{
            userServiceImplementation.deleteUser(id);

            ra.addFlashAttribute("message", "Users deleted successfully");

            } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user/list";
    }







    @GetMapping("/user/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        try{
            User savedUser = userServiceImplementation.findUserById(id);

            if (savedUser != null) {
                model.addAttribute("user", savedUser);
                model.addAttribute("pageTitle", "Edit User");
                return "editUser";
            } else {
                model.addAttribute("message", "User not found");
                return "redirect:/user/list";
            }
        }catch (UserNotFoundException ex){
            ex.printStackTrace();
        }
        return "User Updated successfully";
    }

    @PostMapping("/user/saveEdit")
    public String saveEditSignupPage(User user, Model model, RedirectAttributes ra){
        try {
            // Check if the username already exists
            if (userServiceImplementation.usernameExists(user.getUsername())) {
                ra.addFlashAttribute("message", "Username already exists. Please choose a different username.");
                return "redirect:/user/list";
            }

            User savedUser = userServiceImplementation.saveUser(user);

            // Send confirmation email
            String subject = "User Saved Successfully";
            String text = "Dear " + user.getUsername() + ",\n\nYour Account has been Updated successfully.";

            emailService.sendConfirmationEmail(user.getEmail(), subject, text);



            model.addAttribute("pageTitle","Edit User");
            model.addAttribute("user", savedUser);
            ra.addFlashAttribute("message", "Account Updated successfully.");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/user/list";
    }



@GetMapping("/adminDashboard")
public  String showAdminDashboard(){
        return "adminDashboard";
}




    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
}
