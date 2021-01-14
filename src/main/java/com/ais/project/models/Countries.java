package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Countries {
    @Id
    Integer id_country;
    String country;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    private Collection<Card> cards;

    public Integer getId_country() {
        return id_country;
    }

    public void setId_country(Integer id_country) {
        this.id_country = id_country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
