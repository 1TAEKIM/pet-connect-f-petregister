package com.dogmeat.petconnect.petregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetConnectController {

    @GetMapping("/pet_register")
    public String showRegisterPage(Model model) {
        // You can add attributes to the model if necessary
        return "pet_register"; // This refers to pet_register.html in the templates folder
    }
}
