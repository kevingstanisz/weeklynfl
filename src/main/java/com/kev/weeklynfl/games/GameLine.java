package com.kev.weeklynfl.games;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.kev.weeklynfl.bets.Bet;
import org.apache.commons.lang3.math.Fraction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class GameLine {

    @Id
    private UUID id;

    private String team1;
    private String team2;
    private double sp1;
    private double sp2;
    private Integer sp1Odds;
    private Integer sp2Odds;
    private Integer ml1;
    private Integer ml2;
    private double over;
    private double under;
    private Integer overOdds;
    private Integer underOdds;

    @OneToOne
    private Bet bet;

    @JsonCreator
    public GameLine(){

    }

    public GameLine(UUID id, String team1, String team2, double sp1, double sp2, Integer sp1Odds, Integer sp2Odds, Integer ml1, Integer ml2, double over, double under, Integer overOdds, Integer underOdds) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.sp1 = sp1;
        this.sp2 = sp2;
        this.sp1Odds = sp1Odds;
        this.sp2Odds = sp2Odds;
        this.ml1 = ml1;
        this.ml2 = ml2;
        this.over = over;
        this.under = under;
        this.overOdds = overOdds;
        this.underOdds = underOdds;
        this.bet = new Bet(null, id,false,0,false,0,false,0,false,0,false,0,false,0);
    }

    public GameLine(String htmlTeam1, String htmlTeam2, String htmlSp1, String htmlSp2, String htmlSp1Odds, String htmlSp2Odds, String htmlMl1, String htmlMl2, String htmlOver, String htmlUnder, String htmlOverOdds, String htmlUnderOdds) {
        String IntegerMAX_VALUE = "32767";

        this.team1 = htmlTeam1;
        this.team2 = htmlTeam2;
        this.sp1 = Double.parseDouble(htmlSp1.replace("½", ".5").replace("pk", "0"));
        this.sp2 = Double.parseDouble(htmlSp2.replace("½", ".5").replace("pk", "0"));
        this.sp1Odds = Integer.parseInt(htmlSp1Odds.replace(" ", IntegerMAX_VALUE));
        this.sp2Odds = Integer.parseInt(htmlSp2Odds.replace(" ", IntegerMAX_VALUE));
        this.ml1 = Integer.parseInt(htmlMl1.replace(" ", IntegerMAX_VALUE));
        this.ml2 = Integer.parseInt(htmlMl2.replace(" ", IntegerMAX_VALUE));
        this.over = Double.parseDouble(htmlOver.replace("½", ".5").replace(" ", "0"));
        this.under = Double.parseDouble(htmlUnder.replace("½", ".5").replace(" ", "0"));
        this.overOdds = Integer.parseInt(htmlOverOdds.replace(" ", IntegerMAX_VALUE));
        this.underOdds = Integer.parseInt(htmlUnderOdds.replace(" ", IntegerMAX_VALUE));
    }


    public Bet getBets() {
        return bet;
    }

    public void setBets(Bet bet) {
        this.bet = bet;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getSp1() {
        return sp1;
    }

    public double getSp2() {
        return sp2;
    }

    public UUID getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Integer getSp1Odds() {
        return sp1Odds;
    }

    public Integer getSp2Odds() {
        return sp2Odds;
    }

    public Integer getMl1() {
        return ml1;
    }

    public Integer getMl2() {
        return ml2;
    }

    public double getOver() {
        return over;
    }

    public double getUnder() {
        return under;
    }

    public Integer getOverOdds() {
        return overOdds;
    }

    public Integer getUnderOdds() {
        return underOdds;
    }

    @Override
    public String toString() {
        return "GameLine{" +
                "id=" + id +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", sp1=" + sp1 +
                ", sp2=" + sp2 +
                ", sp1Odds=" + sp1Odds +
                ", sp2Odds=" + sp2Odds +
                ", ml1=" + ml1 +
                ", ml2=" + ml2 +
                ", over=" + over +
                ", under=" + under +
                ", overOdds=" + overOdds +
                ", underOdds=" + underOdds +
                ", bet=" + bet +
                '}';
    }
}
