package com.kev.weeklynfl.bets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@JsonIgnoreProperties(allowGetters = true)
public class Bet {

    @Id
    private Integer id;

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

    public Bet(Integer id, UUID gameId, boolean sp1, Integer sp1Value, boolean sp2, Integer sp2Value, boolean ml1, Integer ml1Value, boolean ml2, Integer ml2Value, boolean over, Integer overValue, boolean under, Integer underValue) {
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

    public Integer getId() {
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
