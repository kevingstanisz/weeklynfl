package com.kev.weeklynfl.games;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import org.apache.commons.lang3.math.Fraction;

import javax.persistence.GeneratedValue;

public class GameLine {
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

    public GameLine(String htmlTeam1, String htmlTeam2, String htmlSp1, String htmlSp2, String htmlSp1Odds, String htmlSp2Odds, String htmlMl1, String htmlMl2, String htmlOver, String htmlUnder, String htmlOverOdds, String htmlUnderOdds) {
        this.team1 = htmlTeam1;
        this.team2 = htmlTeam2;
        this.sp1 = Double.parseDouble(htmlSp1.replace("½", ".5").replace("pk", "0"));
        this.sp2 = Double.parseDouble(htmlSp2.replace("½", ".5").replace("pk", "0"));
        this.sp1Odds = Integer.parseInt(htmlSp1Odds.replace(" ", "0"));
        this.sp2Odds = Integer.parseInt(htmlSp2Odds.replace(" ", "0"));
        this.ml1 = Integer.parseInt(htmlMl1.replace(" ", "0"));
        this.ml2 = Integer.parseInt(htmlMl2.replace(" ", "0"));
        this.over = Double.parseDouble(htmlOver.replace("½", ".5").replace(" ", "0"));
        this.under = Double.parseDouble(htmlUnder.replace("½", ".5").replace(" ", "0"));
        this.overOdds = Integer.parseInt(htmlOverOdds.replace(" ", "0"));
        this.underOdds = Integer.parseInt(htmlUnderOdds.replace(" ", "0"));
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
