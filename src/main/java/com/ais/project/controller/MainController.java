package com.ais.project.controller;

import com.ais.project.models.*;
import com.ais.project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

        HashMap<Integer, ArrayList<String>> expectedValueOffices = new HashMap<>();
        for (String strInit : cardRepository.expectedValueInitiation()) {
            String[] strInit2 = strInit.split(",");
            expectedValueOffices.put(Integer.parseInt(strInit2[0]), new ArrayList<>(Collections.singleton(strInit2[1])));
        }

        for (String strPrepRep : cardRepository.expectedValuePreparingReport()) {
            hashMapOffices(expectedValueOffices, strPrepRep);
        }

        for (String strDec : cardRepository.expectedValueDecision()) {
            hashMapOffices(expectedValueOffices, strDec);
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : expectedValueOffices.entrySet()){
            System.out.println(entry.toString());
        }


        model.addAttribute("title", "Статистика");
        model.addAttribute("officesCount", integerListHashMap);
        model.addAttribute("offices", offices);
        return "main";//переход на шаблон main.html
    }

    private void hashMapOffices(HashMap<Integer, ArrayList<String>> expectedValueOffices, String strDec) {
        String[] strDec2 = strDec.split(",");
        for(Map.Entry<Integer, ArrayList<String>> entry : expectedValueOffices.entrySet()) {
            if (entry.getKey() == Integer.parseInt(strDec2[0])){
                entry.getValue().add(strDec2[1]);
            }
        }
        expectedValueOffices.put(Integer.parseInt(strDec2[0]), new ArrayList<>(Collections.singleton(strDec2[1])));
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
    public String seach(Model model, @RequestParam(value = "id") long id){
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