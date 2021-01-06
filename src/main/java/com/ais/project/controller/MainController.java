package com.ais.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class MainController{

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


}