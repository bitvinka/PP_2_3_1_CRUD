package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/showUserById")
    public String getUserById(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            return "redirect:/";
        }
        if (userService.getUserById(id) == null) {
            model.addAttribute("message", "Пользователя с ID " + id + " не существует. ");
        }
        model.addAttribute("user", userService.getUserById(id));
        return "user";

    }


    @GetMapping("/showUser")
    public String getUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }


    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }


    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "newUserForm";
    }

    @PostMapping("/addNewUser")
    public String addUserForm(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "index";
    }


    @GetMapping("/user/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("editUser", userService.getUserById(id));
        return "editUserForm";
    }

    @PostMapping("/user/edit")
    public String editUserForm(@ModelAttribute("editUser") User user, @RequestParam(value = "id") Long id) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return "redirect:/users";
    }


}
