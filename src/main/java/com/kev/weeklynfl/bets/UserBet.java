package com.kev.weeklynfl.bets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class UserBet {

    @Id
    private String username;

    @OneToMany(targetEntity=Bet.class, mappedBy="username", fetch= FetchType.EAGER)
    private List<Bet> betList;

    @JsonCreator
    public UserBet(){

    }

    public UserBet(String username, List<Bet> betList) {
        this.username = username;
        this.betList = betList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Bet> getBetList() {
        return betList;
    }

    public void setBetList(List<Bet> betList) {
        this.betList = betList;
    }
}
