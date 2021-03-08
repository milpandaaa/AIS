package com.ais.project.repo;

import com.ais.project.models.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    @Query( value = "SELECT avg( DATEDIFF (STR_TO_DATE(date_of_initiation, '%Y-%m-%d'), STR_TO_DATE(date_of_commission, '%Y-%m-%d')) ),\n" +
            "avg( DATEDIFF (STR_TO_DATE(date_of_preparing_report, '%Y-%m-%d'), STR_TO_DATE(date_of_initiation, '%Y-%m-%d')) ),\n" +
            "avg( DATEDIFF (STR_TO_DATE(date_of_decision, '%Y-%m-%d'), STR_TO_DATE(date_of_preparing_report, '%Y-%m-%d')) ),\n" +
            "avg( DATEDIFF (STR_TO_DATE(date_of_entry_into_force, '%Y-%m-%d'), STR_TO_DATE(date_of_decision, '%Y-%m-%d')) ),\n" +
            "avg( DATEDIFF (STR_TO_DATE(date_sentence_enforcement, '%Y-%m-%d'), STR_TO_DATE(date_of_entry_into_force, '%Y-%m-%d')) ) FROM ais.card " ,
            nativeQuery = true)
    public String expectedValue();

    @Query( value = "SELECT id_office," +
            "avg( DATEDIFF (STR_TO_DATE(date_of_decision, '%Y-%m-%d'), STR_TO_DATE(date_of_preparing_report, '%Y-%m-%d')) )" +
            "FROM ais.card, ais.offices" +
            "where card.office_of_preparing_report = offices.id_office " +
            "group by id_office;" ,
            nativeQuery = true)
    public String[] expectedValuePreparingReport();


    @Query( value = "SELECT id_office, " +
            "avg( DATEDIFF (STR_TO_DATE(date_of_preparing_report, '%Y-%m-%d'), STR_TO_DATE(date_of_initiation, '%Y-%m-%d')) )" +
            "FROM ais.card, ais.offices " +
            "where card.office_of_initiation = offices.id_office " +
            "group by id_office;",nativeQuery = true)
    public String[] expectedValueInitiation();

    @Query( value = "SELECT id_office," +
            "avg( DATEDIFF (STR_TO_DATE(date_of_entry_into_force, '%Y-%m-%d'), STR_TO_DATE(date_of_decision, '%Y-%m-%d')) )" +
            "FROM ais.card, ais.offices" +
            "where card.office_of_decision = offices.id_office" +
            "group by id_office;", nativeQuery = true)
    public String[] expectedValueDecision();


//    public List<Card> findByGetDateOfInitiation();
}
