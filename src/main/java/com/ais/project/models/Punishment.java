package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Punishment {
    @Id
    private Integer id_punishment;
    private String name_punishment;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "punishment")
    private Collection<Card> cards;

    @Override
    public String toString() {
        return "Punishment{" +
                "id_punishment=" + id_punishment +
                ", name_punishment='" + name_punishment + '\'' +
                '}';
    }

    public Integer getId_punishment() {
        return id_punishment;
    }

    public void setId_punishment(Integer id_punishment) {
        this.id_punishment = id_punishment;
    }

    public String getName_punishment() {
        return name_punishment;
    }

    public void setName_punishment(String name_punishment) {
        this.name_punishment = name_punishment;
    }
}
