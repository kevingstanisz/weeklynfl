package com.kev.weeklynfl.bets;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.games.GameLine;
import com.kev.weeklynfl.games.WeekNumber;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class BetService {
    private final JdbcTemplate jdbcTemplate;

    static final int WIN = 1;
    static final int LOSS = 0;
    static final int PUSH = 2;

    public BetService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveBets(List<GameLine> gameLines) {
        WeekNumber weekNumber = new WeekNumber();
        UUID testUUID = UUID.fromString("62753844-3272-4867-b1b5-ebd19c525a80");

        String sql =  "DELETE FROM bets WHERE week=" + weekNumber.getWeekNumber() + " AND userid='" + testUUID  + "'";
        jdbcTemplate.execute(sql);

        Map<Integer, Integer> betAndValues = new HashMap<Integer, Integer>();

        for (GameLine gameLine : gameLines) {
            Bet gameBet = gameLine.getBets();

            if(gameBet.isSp1()) {
                betAndValues.put(1, gameBet.getSp1Value());
            }
            if(gameBet.isSp2()) {
                betAndValues.put(2, gameBet.getSp2Value());
            }
            if(gameBet.isMl1()) {
                betAndValues.put(3, gameBet.getMl1Value());
            }
            if(gameBet.isMl2()) {
                betAndValues.put(4, gameBet.getMl2Value());
            }
            if(gameBet.isOver()) {
                betAndValues.put(5, gameBet.getOverValue());
            }
            if(gameBet.isUnder()) {
                betAndValues.put(6, gameBet.getUnderValue());
            }

            for(Map.Entry<Integer, Integer> entry : betAndValues.entrySet()) {
                sql = "INSERT INTO bets (userid, gameid, bettype, betvalue, betresult, totalwon, id, week) " +
                        "VALUES ('" + testUUID + "', '" + gameLine.getId() + "', " + entry.getKey() + ", " + entry.getValue() + ", " + -1 + ", " + -1 + ", '" + UUID.randomUUID() + "', " + weekNumber.getWeekNumber() + ")";

                jdbcTemplate.execute(sql);
            }

            betAndValues.clear();
        }
    }

    public void gradeBets() {
        WeekNumber weekNumber = new WeekNumber();
        String sql = "SELECT id, gameid, bettype, betvalue FROM bets WHERE week=" + 7 + " AND betresult=" + -1  + " ORDER BY gameid ASC";

        List<UUID> gameIndex = new ArrayList<>();
        List<Bet> rawBets = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.add(UUID.fromString(resultSet.getString("gameid")));
            return new Bet(
                    UUID.fromString(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("gameid")),
                    Integer.parseInt(resultSet.getString("betvalue")),
                    Integer.parseInt(resultSet.getString("bettype")));
        });

        sql = "SELECT * FROM games WHERE week=" + 7 + " AND homeresult IS NOT NULL";

        List<GameLine> gameScores = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    Double.parseDouble(resultSet.getString("sphome")),
                    Double.parseDouble(resultSet.getString("spaway")),
                    Integer.parseInt(resultSet.getString("sphomeodds")),
                    Integer.parseInt(resultSet.getString("spawayodds")),
                    Integer.parseInt(resultSet.getString("mlhome")),
                    Integer.parseInt(resultSet.getString("mlaway")),
                    Double.parseDouble(resultSet.getString("totalover")),
                    Double.parseDouble(resultSet.getString("totalunder")),
                    Integer.parseInt(resultSet.getString("overodds")),
                    Integer.parseInt(resultSet.getString("underodds")),
                    Integer.parseInt(resultSet.getString("homeresult")),
                    Integer.parseInt(resultSet.getString("awayresult")));
        });

        for(GameLine gameScore : gameScores) {
            while(gameIndex.contains(gameScore.getId())) {
                double score1 = (double) gameScore.getResult1();
                double score2 = (double) gameScore.getResult2();

                Bet currentBet = rawBets.get(gameIndex.indexOf(gameScore.getId()));

                double totalWon = 0;
                int result = LOSS;
                int odds = 0;

                switch (currentBet.getBetType()) {
                    case 1:
                        odds = gameScore.getSp1Odds();
                        if(score1 + gameScore.getSp1() > score2){
                            result = WIN;
                        }
                        else if(score1 + gameScore.getSp1() < score2){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    case 2:
                        odds = gameScore.getSp2Odds();
                        if(score2 + gameScore.getSp2() > score1){
                            result = WIN;
                        }
                        else if(score2 + gameScore.getSp2() < score1){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    case 3:
                        odds = gameScore.getMl1();
                        if(score1 > score2){
                            result = WIN;
                        }
                        else if(score1 < score2){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    case 4:
                        odds = gameScore.getMl2();
                        if(score2  > score1){
                            result = WIN;
                        }
                        else if(score2 < score1){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    case 5:
                        odds = gameScore.getOverOdds();
                        if((score1 + score2) > gameScore.getOver()){
                            result = WIN;
                        }
                        else if((score1 + score2) < gameScore.getOver()){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    case 6:
                        odds = gameScore.getUnderOdds();
                        if((score1 + score2) < gameScore.getOver()){
                            result = WIN;
                        }
                        else if((score1 + score2) > gameScore.getOver()){
                            result = LOSS;
                        }
                        else {
                            result = PUSH;
                        }
                        break;

                    default:
                        result = LOSS;

                }

                double decimalCalc = 0;

                switch(result){
                    case WIN:
                        if(odds > 0){
                            decimalCalc = odds/100.00;
                        }
                        else{
                            decimalCalc = -100.00/odds;
                        }

                        totalWon = currentBet.getBetValue() * (decimalCalc + 1.0);
                        break;
                    case LOSS:
                        totalWon = 0;
                        break;
                    case PUSH:
                        totalWon = currentBet.getBetValue();
                        break;
                }

                totalWon = Math.round(totalWon * 100.0) / 100.0;

                String sqlUpdate = "UPDATE bets SET betresult = " + result + " , totalwon = " + totalWon + " WHERE id='" + currentBet.getId() + "'";
                jdbcTemplate.execute(sqlUpdate);

                gameIndex.remove(gameScore.getId());
                rawBets.remove(currentBet);
            }
        }
    }
}