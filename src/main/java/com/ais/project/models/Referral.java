package com.ais.project.models;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card", referencedColumnName = "id_card")
    Card card;
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

    public Referral(Card card) {
        this.card = card;
    }

    public Referral(Referral referral) {
        this.id = referral.id;
        this.card = referral.card;
        this.date_departure = referral.date_departure;
        this.office_departure = referral.office_departure;
        this.date_arrival = referral.date_arrival;
        this.office_arrival = referral.office_arrival;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
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

    public Referral(Card card, String date_departure, Offices office_departure, String date_arrival, Offices office_arrival) {
        this.card = card;
        this.date_departure = date_departure;
        this.office_departure = office_departure;
        this.date_arrival = date_arrival;
        this.office_arrival = office_arrival;
    }

    @Override
    public String toString() {
        return "Referral{" +
                "id=" + id +
                ", card=" + card +
                ", date_departure='" + date_departure + '\'' +
                ", office_departure=" + office_departure +
                ", date_arrival='" + date_arrival + '\'' +
                ", office_arrival=" + office_arrival +
                '}';
    }
}
