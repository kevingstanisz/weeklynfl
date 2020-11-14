package com.kev.weeklynfl.games;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class Team {

    @Id
    private String teamFull;

    private String teamAbr;

    @JsonCreator
    public Team(){

    }

    public Team(String teamFull, String teamAbr) {
        this.teamFull = teamFull;
        this.teamAbr = teamAbr;
    }

    public String getTeamFull() {
        return teamFull;
    }

    public void setTeamFull(String teamFull) {
        this.teamFull = teamFull;
    }

    public String getTeamAbr() {
        return teamAbr;
    }

    public void setTeamAbr(String teamAbr) {
        this.teamAbr = teamAbr;
    }
}
