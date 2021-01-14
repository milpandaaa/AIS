package com.ais.project.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Offices {
    @Id
    Integer id_office;
    String department;
    String office;

    public Integer getId_office() {
        return id_office;
    }

    public void setId_office(Integer id_office) {
        this.id_office = id_office;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "office_of_initiation"),
            @JoinColumn(name = "office_of_preparing_report"),
            @JoinColumn(name = "office_of_decision")
    })
    private Collection<Card> cards;

}
