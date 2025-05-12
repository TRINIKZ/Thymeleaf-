package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute UserModel user, Model model) {
        Optional<UserModel> usuario = userRepository.findByEmail(user.getEmail());

        if (usuario.isPresent() && usuario.get().getPassword().equals(user.getPassword())) {
            model.addAttribute("users", userRepository.findAll());
            return "usuarios";
        } else {
            model.addAttribute("error", "E-mail ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/usuarios")
    public String showUsuarios(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "usuarios";
    }
}
