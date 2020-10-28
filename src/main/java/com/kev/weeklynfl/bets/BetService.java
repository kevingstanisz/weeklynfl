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

        Map<UUID, Integer> gameIndex = new HashMap<UUID, Integer>();
        List<Bet> rawBets = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.put(UUID.fromString(resultSet.getString("gameid")), i);
            return new Bet(
                    UUID.fromString(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("gameid")),
                    Integer.parseInt(resultSet.getString("betvalue")),
                    Integer.parseInt(resultSet.getString("bettype")));
        });

        sql = "SELECT id, homeresult, awayresult FROM games WHERE week=" + 7 + " AND homeresult IS NOT NULL";

        List<GameLine> gameScores = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    Integer.parseInt(resultSet.getString("homeresult")),
                    Integer.parseInt(resultSet.getString("awayresult")));

        });

        for(GameLine gameScore : gameScores) {
            if(gameIndex.containsKey(gameScore.getId())) {
                System.out.println("grade the bet");
            }
        }

    }
}