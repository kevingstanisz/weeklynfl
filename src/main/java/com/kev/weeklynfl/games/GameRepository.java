package com.kev.weeklynfl.games;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends CrudRepository<GameLine, UUID> {
    @Query(
            value = "select id, name, phone_number " + "from customer where phone_number = :phone_number",
            nativeQuery = true
    )

    Optional<GameLine> selectCustomerByPhoneNumber(
            @Param("phone_number") String team1
    );

}
