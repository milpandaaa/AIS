package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Article {
    @Id
    Integer id_article;
    String description;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_article")
    private Collection<Card> cards;

    public Integer getId_article() {
        return id_article;
    }

    public void setId_article(Integer id_article) {
        this.id_article = id_article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
