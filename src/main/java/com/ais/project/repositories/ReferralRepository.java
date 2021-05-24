package com.ais.project.repositories;

import com.ais.project.models.Card;
import com.ais.project.models.Referral;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReferralRepository extends CrudRepository<Referral, Integer> {
    Optional<Referral> findByCard(Card card);
    Iterable<Referral> findAllByCard(Card card);

}
