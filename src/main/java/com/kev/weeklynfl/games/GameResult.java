package com.kev.weeklynfl.games;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class GameResult {

    @Id
    private UUID id;

    private UUID team1;
    private UUID team2;
    private Integer result1;
    private Integer result2;

    @JsonCreator
    public GameResult(){

    }

    public GameResult(UUID id, UUID team1, UUID team2, Integer result1, Integer result2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.result1 = result1;
        this.result2 = result2;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTeam1() {
        return team1;
    }

    public void setTeam1(UUID team1) {
        this.team1 = team1;
    }

    public UUID getTeam2() {
        return team2;
    }

    public void setTeam2(UUID team2) {
        this.team2 = team2;
    }

    public Integer getResult1() {
        return result1;
    }

    public void setResult1(Integer result1) {
        this.result1 = result1;
    }

    public Integer getResult2() {
        return result2;
    }

    public void setResult2(Integer result2) {
        this.result2 = result2;
    }
}
