package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("request")
public class controller {
    @GetMapping ("get")
    public String nome(Model model){
        model.addAttribute("Mensagemalgumacoisa", "hello word sla");
        return "index";
    }
}
