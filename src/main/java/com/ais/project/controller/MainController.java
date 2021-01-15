package com.ais.project.controller;

import com.ais.project.models.*;
import com.ais.project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
class MainController{

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private NameRepository nameRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private PatronymicRepository patronymicRepository;
    @Autowired
    private PunishmentRepository punishmentRepository;
    @Autowired
    private ReferralRepository referralRepository;
    @Autowired
    private GenderRepository genderRepository;


    @GetMapping("/")
    public String main(Model model) {//всегда принимает модель
        model.addAttribute("title", "Главная страница");//передаем параметры
        return "main";//переход на шаблон main.html
    }

    @GetMapping("/new/")
    public String add(Model model) {
        Iterable<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.ASC, "description"));
        model.addAttribute("articles", articles);
        Iterable<Countries> countries = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "country"));
        model.addAttribute("countries", countries);
        Iterable<Names> names = nameRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("names", names);
        Iterable<Offices> offices = officeRepository.findAll(Sort.by(Sort.Direction.ASC, "department"));
        model.addAttribute("offices", offices);
        Iterable<Patronymics> patronymics = patronymicRepository.findAll(Sort.by(Sort.Direction.ASC, "patronymic"));
        model.addAttribute("patronymics", patronymics);
        Iterable<Punishment> punishments = punishmentRepository.findAll();
        model.addAttribute("punishments", punishments);
        Iterable<Gender> genders = genderRepository.findAll();
        model.addAttribute("genders", genders);
        model.addAttribute("title", "Добавление");
        return "add";
    }

    @PostMapping("/new/")
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
        String fullId = id_card_1.toString()+id_card_2.toString()+id_card_3.toString()+id_card_4.toString();
        Card card = new Card(Long.parseLong(fullId), last_name, first_name, patronymic, date_of_birth,
                gender, country, region, outdoors,
                time_of_commission, date_of_commission, place_of_commission,
                date_of_initiation, office_of_initiation, name_of_initiation,
                date_of_preparing_report, office_of_preparing_report, name_of_preparing_report, id_article,
                date_of_decision, decision, office_of_decision, name_of_decision, punishment, sum,
                date_of_entry_into_force,
                date_sentence_enforcement, amount);
        cardRepository.save(card);
        Referral referral = new Referral(Long.parseLong(fullId), date_departure, office_departure,
                date_arrival, office_arrival);
        referralRepository.save(referral);
        return "redirect:/all/";
    }


    @GetMapping("/all/")
    public String all(Model model) {//всегда принимает модель
        Iterable<Card> cards = cardRepository.findAll();
        model.addAttribute("cards", cards);
        model.addAttribute("title", "Просмотр");//передаем параметры
        return "all";
    }

    @GetMapping("/newName/")
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

    @PostMapping("/newName/")
    public String addNewName(Model model, @RequestParam String name ){
        Names newName = new Names(name);
        nameRepository.save(newName);
        return "redirect:/newName/";
    }

    @GetMapping("/newPatronymic/")
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

    @PostMapping("/newPatronymic/")
    public String addNewPatronymic(Model model, @RequestParam String name){
        Patronymics newPatronymic = new Patronymics(name);
        patronymicRepository.save(newPatronymic);
        return "redirect:/newPatronymic/";
    }

}