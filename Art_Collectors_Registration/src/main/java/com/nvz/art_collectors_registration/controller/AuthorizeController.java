package com.nvz.art_collectors_registration.controller;

import com.nvz.art_collectors_registration.dto.ArtCollectorDto;
import com.nvz.art_collectors_registration.entity.ArtCollector;
import com.nvz.art_collectors_registration.service.ArtCollectorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorizeController {
    private ArtCollectorService artCollectorService;
    // handles home endpoint requests and returns the index
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // handles art collectors registration form
    @GetMapping("/register")
    public String register(Model model) {
        ArtCollectorDto artCollector = new ArtCollectorDto();
        model.addAttribute("artCollector", artCollector);
        return "art-collectors-registration";
    }

    // handle registration form submit
    @PostMapping("/register/save")
    public String registerArtCollector(@Valid @ModelAttribute("artCollector") ArtCollector artCollector, BindingResult bindingResult, Model model) {
        ArtCollector collectorExist = artCollectorService.findArtCollectorByEmail(artCollector.getEmail());

        if (collectorExist != null) {
            bindingResult.rejectValue("email", "Art collector already exist");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("artCollector", artCollector);
            return "index";
        }

        artCollectorService.saveArtCollector(artCollector);
        return "redirect:/register?success";
    }

    // handles a list of art collectors
    @GetMapping("/artcollectors")
    public String listArtcollectors(Model model) {
        List<ArtCollector> artCollectors = artCollectorService.findAllArtCollectors();
        model.addAttribute("artCollectors", artCollectors);
        return "registered-art-collectors";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

