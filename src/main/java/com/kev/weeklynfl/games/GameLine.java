package com.kev.weeklynfl.games;

import javax.persistence.GeneratedValue;

public class GameLine {
    private String team1;
    private String team2;
    private double sp1;
    private double sp2;
    private int sp1Odds;
    private int sp2Odds;
    private int ml1;
    private int ml2;
    private double over;
    private double under;
    private int overOdds;
    private int underOdds;

    public GameLine(String team1, String team2, double sp1, double sp2, int sp1Odds, int sp2Odds, int ml1, int ml2, double over, double under, int overOdds, int underOdds) {
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
    }

    @Override
    public String toString() {
        return "GameLine{" +
                "team1='" + team1 + '\'' +
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
                '}';
    }
}
