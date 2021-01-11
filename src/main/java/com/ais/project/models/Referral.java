package com.ais.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer card;
    String dateDeparture;
    Integer officeDeparture;
    String dateArrival;
    Integer officeArrival;

    public Referral() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public Integer getOfficeDeparture() {
        return officeDeparture;
    }

    public void setOfficeDeparture(Integer officeDeparture) {
        this.officeDeparture = officeDeparture;
    }

    public String getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Integer getOfficeArrival() {
        return officeArrival;
    }

    public void setOfficeArrival(Integer officeArrival) {
        this.officeArrival = officeArrival;
    }
}
