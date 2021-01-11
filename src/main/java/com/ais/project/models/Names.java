package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Names {
    @Id
    private Integer id_name;
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="first_name")
    private Collection<Card> cards;

    public Integer getId_name() {
        return id_name;
    }

    public void setId_name(Integer id_name) {
        this.id_name = id_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
