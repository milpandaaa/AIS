package com.ais.project.repo;

import com.ais.project.models.Referral;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReferralRepository extends CrudRepository<Referral, Integer> {
    Optional<Referral> findByCard(long card);
}
