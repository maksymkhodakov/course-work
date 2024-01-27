package com.example.zoo.controller.mvc;

import com.example.zoo.data.LoginData;
import com.example.zoo.data.RegisterData;
import com.example.zoo.services.SecureBasicAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    public static final String LOGIN = "login";
    private final SecureBasicAuthenticationService secureBasicAuthenticationService;

    @GetMapping("/")
    public String getLoginForm(Model model) {
        final LoginData user = new LoginData();
        model.addAttribute("user", user);
        return LOGIN;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") LoginData user,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return LOGIN;
        }
        try {
            secureBasicAuthenticationService.login(user);
        } catch (Exception e) {
            model.addAttribute("user", user);
            return LOGIN;
        }
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        final RegisterData user = new RegisterData();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") RegisterData user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        secureBasicAuthenticationService.register(user);
        return "redirect:/register?success";
    }
}
