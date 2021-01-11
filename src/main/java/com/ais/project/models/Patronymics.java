package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Patronymics {
    @Id
    private Integer id_patronymic;
    private String patronymic;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "patronymic")
    private Collection<Card> cards;

    public Integer getId_patronymic() {
        return id_patronymic;
    }

    public void setId_patronymic(Integer id_patronymic) {
        this.id_patronymic = id_patronymic;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
