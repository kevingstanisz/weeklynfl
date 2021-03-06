package com.kev.weeklynfl.bets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kev.weeklynfl.games.GameLine;
import com.kev.weeklynfl.games.GameResult;
import com.kev.weeklynfl.games.Team;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class Bet {

    @Id
    private UUID id;

    private UUID gameId;
    private boolean sp1;
    private Integer sp1Value;
    private boolean sp2;
    private Integer sp2Value;
    private boolean ml1;
    private Integer ml1Value;
    private boolean ml2;
    private Integer ml2Value;
    private boolean over;
    private Integer overValue;
    private boolean under;
    private Integer underValue;

    private Integer betValue;
    private Integer betType;

    private Integer betResult;
    private double totalWon;
    private String username;
    private Integer userId;

    private Integer week;

    @OneToOne
    private GameLine gameLine;

    @OneToOne
    private Team team1;

    @OneToOne
    private Team team2;


    @JsonCreator
    public Bet(){

    }

    public Bet(UUID id, UUID gameId, boolean sp1, Integer sp1Value, boolean sp2, Integer sp2Value, boolean ml1, Integer ml1Value, boolean ml2, Integer ml2Value, boolean over, Integer overValue, boolean under, Integer underValue) {
        this.id = id;
        this.gameId = gameId;
        this.sp1 = sp1;
        this.sp1Value = sp1Value;
        this.sp2 = sp2;
        this.sp2Value = sp2Value;
        this.ml1 = ml1;
        this.ml1Value = ml1Value;
        this.ml2 = ml2;
        this.ml2Value = ml2Value;
        this.over = over;
        this.overValue = overValue;
        this.under = under;
        this.underValue = underValue;
    }

    public Bet(UUID id, UUID gameId, Integer betValue, Integer betType) {
        this.id = id;
        this.gameId = gameId;
        this.betValue = betValue;
        this.betType = betType;
    }

    public Bet(Integer userID, UUID gameId, Integer betValue, Integer betType, Integer betResult, double totalWon) {
        this.userId = userID;
        this.gameId = gameId;
        this.betValue = betValue;
        this.betType = betType;
        this.betResult = betResult;
        this.totalWon = totalWon;
    }

    public Bet(Integer userID, UUID gameId, Integer betValue, Integer betType, Integer betResult, double totalWon, Integer week) {
        this.userId = userID;
        this.gameId = gameId;
        this.betValue = betValue;
        this.betType = betType;
        this.betResult = betResult;
        this.totalWon = totalWon;
        this.week = week;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public GameLine getGameLine() {
        return gameLine;
    }

    public void setGameLine(GameLine gameLine) {
        this.gameLine = gameLine;
    }

    public void setBetValue(Integer betValue) {
        this.betValue = betValue;
    }

    public void setBetType(Integer betType) {
        this.betType = betType;
    }

    public Integer getBetResult() {
        return betResult;
    }

    public void setBetResult(Integer betResult) {
        this.betResult = betResult;
    }

    public double getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(double totalWon) {
        this.totalWon = totalWon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBetValue() {
        return betValue;
    }

    public Integer getBetType() {
        return betType;
    }

    public UUID getId() {
        return id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public boolean isSp1() {
        return sp1;
    }

    public Integer getSp1Value() {
        return sp1Value;
    }

    public boolean isSp2() {
        return sp2;
    }

    public Integer getSp2Value() {
        return sp2Value;
    }

    public boolean isMl1() {
        return ml1;
    }

    public Integer getMl1Value() {
        return ml1Value;
    }

    public boolean isMl2() {
        return ml2;
    }

    public Integer getMl2Value() {
        return ml2Value;
    }

    public boolean isOver() {
        return over;
    }

    public Integer getOverValue() {
        return overValue;
    }

    public boolean isUnder() {
        return under;
    }

    public Integer getUnderValue() {
        return underValue;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setSp1(boolean sp1) {
        this.sp1 = sp1;
    }

    public void setSp1Value(Integer sp1Value) {
        this.sp1Value = sp1Value;
    }

    public void setSp2(boolean sp2) {
        this.sp2 = sp2;
    }

    public void setSp2Value(Integer sp2Value) {
        this.sp2Value = sp2Value;
    }

    public void setMl1(boolean ml1) {
        this.ml1 = ml1;
    }

    public void setMl1Value(Integer ml1Value) {
        this.ml1Value = ml1Value;
    }

    public void setMl2(boolean ml2) {
        this.ml2 = ml2;
    }

    public void setMl2Value(Integer ml2Value) {
        this.ml2Value = ml2Value;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void setOverValue(Integer overValue) {
        this.overValue = overValue;
    }

    public void setUnder(boolean under) {
        this.under = under;
    }

    public void setUnderValue(Integer underValue) {
        this.underValue = underValue;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", sp1=" + sp1 +
                ", sp1Value=" + sp1Value +
                ", sp2=" + sp2 +
                ", sp2Value=" + sp2Value +
                ", ml1=" + ml1 +
                ", ml1Value=" + ml1Value +
                ", ml2=" + ml2 +
                ", ml2Value=" + ml2Value +
                ", over=" + over +
                ", overValue=" + overValue +
                ", under=" + under +
                ", underValue=" + underValue +
                '}';
    }
}
