package org.example.producermodule.controller;

import com.example.zoo.data.LoginData;
import com.example.zoo.services.SecureBasicAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producer")
@RequiredArgsConstructor
public class AdminSecurityController {
    private static final String LOGIN = "producerlogin";
    private static final String ERROR = "error";
    private final SecureBasicAuthenticationService secureBasicAuthenticationService;

    @GetMapping("/")
    public String getLoginForm(Model model) {
        final LoginData user = new LoginData();
        model.addAttribute("user", user);
        model.addAttribute(ERROR, null);
        return LOGIN;
    }

    @PostMapping("/login")
    public String loginTry(@Valid @ModelAttribute("user") LoginData user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute(ERROR, null);
            return LOGIN;
        }
        try {
            secureBasicAuthenticationService.login(user);
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute(ERROR, e.getMessage());
            return LOGIN;
        }
        return "redirect:/home";
    }
}
