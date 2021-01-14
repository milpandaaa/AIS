package com.ais.project.models;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    Long id_card;
    String last_name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="first_name", referencedColumnName = "id_name")
    Names first_name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patronymic", referencedColumnName = "id_patronymic")
    Patronymics patronymic;
    String date_of_birth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gender", referencedColumnName = "id_gender")
    Gender gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country", referencedColumnName = "id_country")
    Countries country;
    Integer region;
    String outdoors ;
    String time_of_commission;
    String date_of_commission;
    String place_of_commission;
    String date_of_initiation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="office_of_initiation", referencedColumnName = "id_office")
    Offices office_of_initiation;
    String name_of_initiation;
    String date_of_preparing_report;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="office_of_preparing_report", referencedColumnName = "id_office")
    Offices office_of_preparing_report;
    String name_of_preparing_report;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_article", referencedColumnName = "id_article")
    Article id_article;
    String date_of_decision;
    String decision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="office_of_decision", referencedColumnName = "id_office")
    Offices office_of_decision;
    String name_of_decision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="punishment", referencedColumnName = "id_punishment")
    Punishment punishment;
    Integer sum;
    String date_of_entry_into_force;
    String date_sentence_enforcement;
    Integer amount;

    public Card() {
    }

    public Long getId_card() {
        return id_card;
    }

    public String getFull_name() {
        if (last_name==null||last_name.equals(""))
            return "";
        else
            return last_name + " " + first_name.getName() + " "+patronymic.getPatronymic();
    }

    public void setId_card (Long id_card ) {
        this.id_card = id_card ;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Names getFirst_name() {
        return first_name;
    }

    public void setFirst_name(Names first_name) {
        this.first_name = first_name;
    }

    public Patronymics getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(Patronymics patronymic) {
        this.patronymic = patronymic;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }


    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getOutdoors() {
        return outdoors;
    }

    public void setOutdoors(String outdoors) {
        this.outdoors = outdoors;
    }

    public String getDate_of_commission() {
        return date_of_commission;
    }

    public void setDate_of_commission(String date_of_commission) {
        this.date_of_commission = date_of_commission;
    }

    public String getPlace_of_commission() {
        return place_of_commission;
    }

    public void setPlace_of_commission(String place_of_commission) {
        this.place_of_commission = place_of_commission;
    }

    public String getDate_of_initiation() {
        return date_of_initiation;
    }

    public void setDate_of_initiation(String date_of_initiation) {
        this.date_of_initiation = date_of_initiation;
    }



    public String getName_of_initiation() {
        return name_of_initiation;
    }

    public void setName_of_initiation(String name_of_initiation) {
        this.name_of_initiation = name_of_initiation;
    }

    public String getDate_of_preparing_report() {
        return date_of_preparing_report;
    }

    public void setDate_of_preparing_report(String date_of_preparing_report) {
        this.date_of_preparing_report = date_of_preparing_report;
    }



    public String getName_of_preparing_report() {
        return name_of_preparing_report;
    }

    public void setName_of_preparing_report(String name_of_preparing_report) {
        this.name_of_preparing_report = name_of_preparing_report;
    }


    public String getDate_of_decision() {
        return date_of_decision;
    }

    public void setDate_of_decision(String date_of_decision) {
        this.date_of_decision = date_of_decision;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getName_of_decision() {
        return name_of_decision;
    }

    public void setName_of_decision(String name_of_decision) {
        this.name_of_decision = name_of_decision;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getDate_of_entry_into_force() {
        return date_of_entry_into_force;
    }

    public void setDate_of_entry_into_force(String date_of_entry_into_force) {
        this.date_of_entry_into_force = date_of_entry_into_force;
    }

    public String getDate_sentence_enforcement() {
        return date_sentence_enforcement;
    }

    public void setDate_sentence_enforcement(String date_sentence_enforcement) {
        this.date_sentence_enforcement = date_sentence_enforcement;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTime_of_commission() {
        return time_of_commission;
    }

    public void setTime_of_commission(String time_of_commission) {
        this.time_of_commission = time_of_commission;
    }

    public Card(Long id_card, String last_name, Names first_name, Patronymics patronymic, String date_of_birth,
                Gender gender, Countries country, Integer region, String outdoors, String time_of_commission,
                String date_of_commission, String place_of_commission, String date_of_initiation,
                Offices office_of_initiation, String name_of_initiation, String date_of_preparing_report,
                Offices office_of_preparing_report, String name_of_preparing_report, Article id_article,
                String date_of_decision, String decision, Offices office_of_decision, String name_of_decision,
                Punishment punishment, Integer sum, String date_of_entry_into_force, String date_sentence_enforcement,
                Integer amount) {
        this.id_card = id_card;
        this.last_name = last_name;
        this.first_name = first_name;
        this.patronymic = patronymic;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.country = country;
        this.region = region;
        this.outdoors = outdoors;
        this.time_of_commission = time_of_commission;
        this.date_of_commission = date_of_commission;
        this.place_of_commission = place_of_commission;
        this.date_of_initiation = date_of_initiation;
        this.office_of_initiation = office_of_initiation;
        this.name_of_initiation = name_of_initiation;
        this.date_of_preparing_report = date_of_preparing_report;
        this.office_of_preparing_report = office_of_preparing_report;
        this.name_of_preparing_report = name_of_preparing_report;
        this.id_article = id_article;
        this.date_of_decision = date_of_decision;
        this.decision = decision;
        this.office_of_decision = office_of_decision;
        this.name_of_decision = name_of_decision;
        this.punishment = punishment;
        this.sum = sum;
        this.date_of_entry_into_force = date_of_entry_into_force;
        this.date_sentence_enforcement = date_sentence_enforcement;
        this.amount = amount;
    }
}
