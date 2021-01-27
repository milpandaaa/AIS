package com.ais.project.models;

import javax.persistence.*;

@Entity
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Long card;
    String date_departure;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="office_departure", referencedColumnName = "id_office")
    Offices office_departure;
    String date_arrival;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="office_arrival", referencedColumnName = "id_office")
    Offices office_arrival;

    public Referral() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCard() {
        return card;
    }

    public void setCard(Long card) {
        this.card = card;
    }

    public String getDate_departure() {
        return date_departure;
    }

    public void setDate_departure(String date_departure) {
        this.date_departure = date_departure;
    }

    public Offices getOffice_departure() {
        return office_departure;
    }

    public void setOffice_departure(Offices office_departure) {
        this.office_departure = office_departure;
    }

    public String getDate_arrival() {
        return date_arrival;
    }

    public void setDate_arrival(String date_arrival) {
        this.date_arrival = date_arrival;
    }

    public Offices getOffice_arrival() {
        return office_arrival;
    }

    public void setOffice_arrival(Offices office_arrival) {
        this.office_arrival = office_arrival;
    }

    public Referral(Long card, String date_departure, Offices office_departure, String date_arrival, Offices office_arrival) {
        this.card = card;
        this.date_departure = date_departure;
        this.office_departure = office_departure;
        this.date_arrival = date_arrival;
        this.office_arrival = office_arrival;
    }
}
