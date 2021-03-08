package com.ais.project.controller;

import com.ais.project.models.*;
import com.ais.project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
class MainController{

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
    public String addNewName(Model model, @RequestParam String name ){
        Names newName = new Names(name);
        nameRepository.save(newName);
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
    public String addNewPatronymic(Model model, @RequestParam String name){
        Patronymics newPatronymic = new Patronymics(name);
        patronymicRepository.save(newPatronymic);
        return "redirect:/newPatronymic";
    }


    @RequestMapping(value = "/seach", method=RequestMethod.GET)
    public String seach(Model model, @RequestParam(value = "id") long id){
        if(cardRepository.findById(id).isPresent()) {
            return"redirect:/{id}/edit";
        }
        model.addAttribute("card", "res");
        return null;
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model) {
        if(cardRepository.findById(id).isEmpty()){
            return "redirect:/all";
        }
        Optional<Card> card = cardRepository.findById(id);
        ArrayList<Card> res = new ArrayList<>();
        card.ifPresent(res::add);
        model.addAttribute("card", res);

        Optional<Referral> referral = referralRepository.findByCard(id);
        ArrayList<Referral> referrals = new ArrayList<>();
        referral.ifPresent(referrals::add);
        model.addAttribute("referral", referrals);

        addModel(model);
        model.addAttribute("title", "Изменения административного правонарушения");
        return "edit";//переход на шаблон main.html
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

    @PostMapping("/{id}/edit")
    public String editCard(@PathVariable(value = "id") long id, @RequestParam(required = false) String last_name, @RequestParam(required = false) Names first_name,
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
                           @RequestParam(required = false) String date_arrival, @RequestParam(required = false) Offices office_arrival,
                           Model model ) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.setLast_name(last_name);
        card.setFirst_name(first_name);
        card.setPatronymic(patronymic);
        card.setDate_of_birth(date_of_birth);
        card.setGender(gender);
        card.setCountry(country);
        card.setRegion(region);
        card.setOutdoors(outdoors);
        card.setTime_of_commission(time_of_commission);
        card.setDate_of_commission(date_of_commission);
        card.setPlace_of_commission(place_of_commission);
        card.setDate_of_initiation(date_of_initiation);
        card.setOffice_of_initiation(office_of_initiation);
        card.setName_of_initiation(name_of_initiation);
        card.setDate_of_preparing_report(date_of_preparing_report);
        card.setOffice_of_preparing_report(office_of_preparing_report);
        card.setName_of_preparing_report(name_of_preparing_report);
        card.setId_article(id_article);
        card.setDecision(decision);
        card.setDate_of_decision(date_of_decision);
        card.setOffice_of_decision(office_of_decision);
        card.setName_of_decision(name_of_decision);
        card.setPunishment(punishment);
        card.setSum(sum);
        card.setDate_of_entry_into_force(date_of_entry_into_force);
        card.setDate_sentence_enforcement(date_sentence_enforcement);
        card.setAmount(amount);

        Referral referral = referralRepository.findByCard(id).orElseThrow();
        referral.setDate_departure(date_departure);
        referral.setOffice_departure(office_departure);
        referral.setDate_arrival(date_arrival);
        referral.setOffice_arrival(office_arrival);

        cardRepository.save(card);
        referralRepository.save(referral);
        return "redirect:/all";//переход на шаблон main.html
    }

}