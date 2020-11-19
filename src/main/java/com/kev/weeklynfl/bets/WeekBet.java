package com.kev.weeklynfl.bets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class WeekBet {

    @Id
    private Integer weekNumber;

    @OneToMany(targetEntity=Bet.class, mappedBy="week", fetch= FetchType.EAGER)
    private List<Bet> betList;

    @JsonCreator
    public WeekBet(){

    }

    public WeekBet(Integer weekNumber, List<Bet> betList) {
        this.weekNumber = weekNumber;
        this.betList = betList;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public List<Bet> getBetList() {
        return betList;
    }

    public void setBetList(List<Bet> betList) {
        this.betList = betList;
    }
}
