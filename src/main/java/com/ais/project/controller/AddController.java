package com.ais.project.controller;

import com.ais.project.models.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AddController extends MainController{

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<String> search(@RequestParam(value = "id") long id) {
        return cardRepository.findById(id)
                .map(Card::getId_card)
                .map(String::valueOf)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/new")
    public String add(Model model) {
        addModel(model);
        model.addAttribute("title", "Добавление");
        return "add";
    }


    @PostMapping("/new")
    public String addAP(Model model, @RequestParam Integer id_card_1, @RequestParam Integer id_card_2, @RequestParam Integer id_card_3,
                        @RequestParam Integer id_card_4,
                        @RequestParam(required = false) String last_name, @RequestParam(required = false) Names first_name,
                        @RequestParam(required = false) Patronymics patronymic, @RequestParam(required = false) String date_of_birth,
                        @RequestParam(required = false) Gender gender, @RequestParam(required = false) Countries country,
                        @RequestParam(required = false) Integer region, @RequestParam(required = false) String outdoors,
                        @RequestParam String time_of_commission, @RequestParam String date_of_commission, @RequestParam String place_of_commission,
                        @RequestParam(required = false) String date_of_initiation, @RequestParam(required = false) Offices office_of_initiation,
                        @RequestParam(required = false) String name_of_initiation,
                        @RequestParam(required = false) String date_of_preparing_report,
                        @RequestParam(required = false) Offices office_of_preparing_report,
                        @RequestParam(required = false) String name_of_preparing_report,
                        @RequestParam(required = false) Article id_article, @RequestParam(required = false) String date_of_decision,
                        @RequestParam(required = false) String decision,
                        @RequestParam(required = false) Offices office_of_decision, @RequestParam(required = false) String name_of_decision,
                        @RequestParam(required = false) Punishment punishment, @RequestParam(required = false) Integer sum, @RequestParam(required = false) String date_of_entry_into_force,
                        @RequestParam(required = false) String date_sentence_enforcement, @RequestParam(required = false) Integer amount,
                        @RequestParam(required = false) String date_departure, @RequestParam(required = false) Offices office_departure,
                        @RequestParam(required = false) String date_arrival, @RequestParam(required = false) Offices office_arrival) {
        String fullId = id_card_1.toString() + id_card_2.toString() + id_card_3.toString() + id_card_4.toString();
        Card card = new Card(Long.parseLong(fullId), last_name, first_name, patronymic, date_of_birth,
                gender, country, region, outdoors,
                time_of_commission, date_of_commission, place_of_commission,
                date_of_initiation, office_of_initiation, name_of_initiation,
                date_of_preparing_report, office_of_preparing_report, name_of_preparing_report, id_article,
                date_of_decision, decision, office_of_decision, name_of_decision, punishment, sum,
                date_of_entry_into_force,
                date_sentence_enforcement, amount);
        cardRepository.save(card);
        if( date_departure.length()!=0 ||  date_arrival .length()!=0 ||office_departure!=null || office_arrival!=null)
        {
            Referral referral = new Referral(card, date_departure, office_departure,
                    date_arrival, office_arrival);
            referralRepository.save(referral);
        }
        return "redirect:/all/";
    }


    @GetMapping("/newName")
    public String newName(Model model) {//всегда принимает модель
        List<Names> names = (List<Names>) nameRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        Map<Character, List<String>> collect = names.stream()
                .map(Names::getName)
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        model.addAttribute("nameClass", "Новое имя");
        model.addAttribute("action", "newName");
        model.addAttribute("names", collect);

        model.addAttribute("title", "Добавление имени");//передаем параметры
        return "addNamePatronymic";
    }

    @PostMapping("/newName")
    public String addNewName(Model model, @RequestParam String name) {
        if(nameRepository.findByName(name).size()==0){
            Names newName = new Names(name);
            nameRepository.save(newName);
        }
        return "redirect:/newName";
    }

    @GetMapping("/newPatronymic")
    public String newPatronymic(Model model) {
        List<Patronymics> patronymics = (List<Patronymics>) patronymicRepository.findAll(Sort.by(Sort.Direction.ASC, "patronymic"));
        Map<Character, List<String>> collect = patronymics.stream()
                .map(Patronymics::getPatronymic)
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        model.addAttribute("names", collect);
        model.addAttribute("nameClass", "Новое отчество");
        model.addAttribute("action", "newPatronymic");
        model.addAttribute("title", "Добавление отчества");//передаем параметры
        return "addNamePatronymic";
    }

    @PostMapping("/newPatronymic")
    public String addNewPatronymic(Model model, @RequestParam String name) {
        if(patronymicRepository.findByPatronymic(name).size()==0)
        {
            Patronymics newPatronymic = new Patronymics(name);
            patronymicRepository.save(newPatronymic);
        }
        return "redirect:/newPatronymic";
    }
}
