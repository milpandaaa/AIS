package com.ais.project.controller;

import com.ais.project.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class EditController extends MainController{


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model) {
        if (!cardRepository.findById(id).isPresent()) {
            return "redirect:/all";
        }
        Optional<Card> card = cardRepository.findById(id);
        ArrayList<Card> res = new ArrayList<>();
        card.ifPresent(res::add);
        model.addAttribute("card", res);

        ArrayList<Referral> referrals = (ArrayList<Referral>) referralRepository
                .findAllByCard(res.get(0));
        Referral lastReferral;
        if(referrals.size()>0){
            if(referrals.get(referrals.size()-1).getDate_arrival().length()==0
                    && referrals.get(referrals.size()-1).getOffice_arrival()==null) {
                lastReferral = new Referral(referrals.get(referrals.size() - 1));
                referrals.remove(referrals.size()-1);
            }
            else{
                lastReferral = new Referral(res.get(0));
            }
        }
        else
            lastReferral = new Referral(card.get());
        model.addAttribute("lastReferral", lastReferral);
        model.addAttribute("referrals", referrals);

        addModel(model);
        model.addAttribute("title", "Изменения административного правонарушения");
        return "edit";//переход на шаблон main.html
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
                           Model model) {
        Card card = cardRepository.findById(id).get();
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

        cardRepository.save(card);

        if(date_departure.length()!=0 && office_departure!=null && date_arrival.length()!=0
        && office_arrival!=null){
            ArrayList<Referral> referralList = (ArrayList<Referral>) referralRepository.findAllByCard(card);

            if(referralList.get(referralList.size()-1).getOffice_arrival()==null
                    || referralList.get(referralList.size()-1).getDate_arrival().length()==0){
                Referral referral = referralList.get(referralList.size()-1);
                referral.setDate_departure(date_departure);
                referral.setOffice_departure(office_departure);
                referral.setDate_arrival(date_arrival);
                referral.setOffice_arrival(office_arrival);
                referralRepository.save(referral);
            }

            else{
                Referral referral = new Referral(card, date_departure, office_departure,
                        date_arrival, office_arrival);
                referralRepository.save(referral);
            }

        }
        else{
            if(date_departure.length()!=0 && office_departure!=null){
                Referral referral = new Referral(card, date_departure, office_departure,
                        date_arrival, office_arrival);
                referralRepository.save(referral);
            }
        }
        return "redirect:/all";
    }


}
