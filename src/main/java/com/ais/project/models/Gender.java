package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Gender {
    @Id
    Integer id_gender;
    String gender;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gender")
    private Collection<Card> cards;

    public Integer getId_gender() {
        return id_gender;
    }

    public void setId_gender(Integer id_gender) {
        this.id_gender = id_gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
