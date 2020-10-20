package com.kev.weeklynfl.bets;

import com.kev.weeklynfl.bets.Bet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BetRepository extends CrudRepository<Bet, UUID> {
    @Query(
            value = "select id, name, phone_number " + "from customer where phone_number = :phone_number",
            nativeQuery = true
    )

    Optional<Bet> selectCustomerByPhoneNumber(
            @Param("phone_number") String team1
    );

}