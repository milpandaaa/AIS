package com.ais.project.controller;

import com.ais.project.models.Card;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController extends MainController{

    @GetMapping("/all")
    public String all(Model model) {
        Iterable<Card> cards = cardRepository.findAll();
        model.addAttribute("cards", cards);
        model.addAttribute("title", "Просмотр");
        return "all";
    }
}
