package com.ais.project.controller;

import com.ais.project.models.*;
import com.ais.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
class MainController {

    @Autowired
    protected ArticleRepository articleRepository;
    @Autowired
    protected CardRepository cardRepository;
    @Autowired
    protected CountryRepository countryRepository;
    @Autowired
    protected NameRepository nameRepository;
    @Autowired
    protected OfficeRepository officeRepository;
    @Autowired
    protected PatronymicRepository patronymicRepository;
    @Autowired
    protected PunishmentRepository punishmentRepository;
    @Autowired
    protected ReferralRepository referralRepository;
    @Autowired
    protected GenderRepository genderRepository;

    @GetMapping("/")
    public String main(Model model) {//всегда принимает модель
        Iterable<Offices> offices = officeRepository.findAll();

        List<Card> cards = (List<Card>) cardRepository.findAll();

        HashMap<Integer, List<Integer>> integerListHashMap = new HashMap<>();
        Set<Integer> ids = new HashSet<>();
        for (Card card : cards) {
            add(card.getOffice_of_initiation(), ids);
            add(card.getOffice_of_preparing_report(), ids);
            add(card.getOffice_of_decision(), ids);
        }

        for (Integer id : ids) {
            Integer i1 = 0;
            Integer i2 = 0;
            Integer i3 = 0;
            for (Card card : cards) {
                i1 = count(id, i1, card.getOffice_of_initiation());
                i2 = count(id, i2, card.getOffice_of_preparing_report());
                i3 = count(id, i3, card.getOffice_of_decision());
            }
            List<Integer> integers = Arrays.asList(i1, i2, i3);
            integerListHashMap.put(id, integers);
        }

        model.addAttribute("expectedValueAll", cardRepository.expectedValue().split(","));

        HashMap<String, List<Double>> expectedValueOffices = new HashMap<>();


        addToMap(expectedValueOffices, cardRepository.expectedValueInitiation(), 0);
        addToMap(expectedValueOffices, cardRepository.expectedValuePreparingReport(), 1);
        addToMap(expectedValueOffices, cardRepository.expectedValueDecision(), 2);

        model.addAttribute("expectedValueOffices", expectedValueOffices);

        model.addAttribute("quantityOffices",cardRepository.quantityOffices().split(","));

        model.addAttribute("title", "Статистика");
        model.addAttribute("officesCount", integerListHashMap);
        model.addAttribute("offices", offices);
        return "main";//переход на шаблон main.html
    }

    private static void addToMap(HashMap<String, List<Double>> map, String[][] array, int index) {
        for (String[] strings : array) {
            if (strings == null){
                continue;
            }
            map.compute(strings[0], (key, value) -> {
                if (value == null) {
                    value = Arrays.asList(0d,0d,0d);
                }
                value.set(index, getDigit(strings[1]));
                return value;
            });
        }
    }

    private static double getDigit(String value) {
        return Optional.ofNullable(value)
                .map(str -> {
                    try {
                        return Double.parseDouble(str);
                    } catch (NumberFormatException e) {
                        return 0D;
                    }
                }).orElse(0D);
    }


    private static Integer count(Integer id, Integer i1, Offices office) {
        if (office != null && id.equals(office.getId_office())) {
            i1++;
        }
        return i1;
    }

    private static void add(Offices office, Set<Integer> ids) {
        if (office != null) {
            ids.add(office.getId_office());
        }
    }

    private Long duration(String startDate, String endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        return ( format.parse(startDate).getTime() - format.parse(endDate).getTime() )/ (24 * 60 * 60 * 1000);
    }




    @RequestMapping(value = "/seach", method=RequestMethod.GET)
    public String search(Model model, @RequestParam(value = "id") long id){
        if(cardRepository.findById(id).isPresent()) {
            return"redirect:/{id}/edit";
        }
        model.addAttribute("card", "res");
        return null;
    }

    protected void addModel(Model model) {
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
    }


}