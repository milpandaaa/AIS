package com.ais.project.controller;

import com.ais.project.models.Card;
import com.ais.project.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class MainController{

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/")
    public String main(Model model) {//всегда принимает модель
        model.addAttribute("title", "Главная страница");//передаем параметры
        return "main";//переход на шаблон main.html
    }

    @GetMapping("/new")
    public String add(Model model) {//всегда принимает модель
        model.addAttribute("title", "Добавление");//передаем параметры
        return "add";//переход на шаблон main.html
    }

    @GetMapping("/all")
    public String all(Model model) {//всегда принимает модель
        Iterable<Card> cards = cardRepository.findAll();
        model.addAttribute("cards", cards);
        model.addAttribute("title", "Просмотр");//передаем параметры
        return "all";//переход на шаблон main.html
    }


}