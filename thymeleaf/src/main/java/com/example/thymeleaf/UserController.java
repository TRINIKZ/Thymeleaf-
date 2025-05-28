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
            model.addAttribute("user", user);
            return "login";
        }
    }

    @GetMapping("/cadastro")
    public String showCadastroForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processCadastro(@ModelAttribute UserModel user, Model model) {
        Optional<UserModel> existente = userRepository.findByEmail(user.getEmail());

        if (existente.isPresent()) {
            model.addAttribute("error", "E-mail já cadastrado");
            model.addAttribute("user", user);
            return "cadastro";
        }

        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/usuarios")
    public String showUsuarios(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "usuarios";
    }

    // ✅ Editar usuário (formulário)
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editar"; // você precisa criar editar.html
        } else {
            return "redirect:/usuarios";
        }
    }

    // ✅ Salvar alterações
    @PostMapping("/editar/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserModel updatedUser) {
        Optional<UserModel> existing = userRepository.findById(id);
        if (existing.isPresent()) {
            UserModel user = existing.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            userRepository.save(user);
        }
        return "redirect:/usuarios";
    }

    // ✅ Excluir usuário
    @GetMapping("/excluir/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}
