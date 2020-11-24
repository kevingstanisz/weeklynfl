package com.kev.weeklynfl.bets;

import com.kev.weeklynfl.auth.security.jwt.JwtUtils;
import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.games.GameLine;
import com.kev.weeklynfl.games.GameResult;
import com.kev.weeklynfl.games.Team;
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
public class BetService extends JwtUtils {
    private final JdbcTemplate jdbcTemplate;

    static final int WIN = 1;
    static final int LOSS = 0;
    static final int PUSH = 2;

    public BetService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveBets(List<GameLine> gameLines, String authToken) {
        WeekNumber weekNumber = new WeekNumber();

        String username = getUserNameFromJwtToken(authToken.substring(7));
        String sql = "SELECT id FROM users WHERE username=?";

        Integer userId = (Integer) jdbcTemplate.queryForObject(
                sql, new Object[] { username }, Integer.class);


        if(userId != null) {
            sql = "DELETE FROM bets WHERE week=" + weekNumber.getWeekNumber() + " AND userid=" + userId;
            jdbcTemplate.execute(sql);

            Map<Integer, Integer> betAndValues = new HashMap<Integer, Integer>();

            for (GameLine gameLine : gameLines) {
                Bet gameBet = gameLine.getBets();

                if (gameBet.isSp1()) {
                    betAndValues.put(1, gameBet.getSp1Value());
                }
                if (gameBet.isSp2()) {
                    betAndValues.put(2, gameBet.getSp2Value());
                }
                if (gameBet.isMl1()) {
                    betAndValues.put(3, gameBet.getMl1Value());
                }
                if (gameBet.isMl2()) {
                    betAndValues.put(4, gameBet.getMl2Value());
                }
                if (gameBet.isOver()) {
                    betAndValues.put(5, gameBet.getOverValue());
                }
                if (gameBet.isUnder()) {
                    betAndValues.put(6, gameBet.getUnderValue());
                }

                for (Map.Entry<Integer, Integer> entry : betAndValues.entrySet()) {
                    sql = "INSERT INTO bets (userid, gameid, bettype, betvalue, betresult, totalwon, id, week) " +
                            "VALUES (" + userId + ", '" + gameLine.getId() + "', " + entry.getKey() + ", " + entry.getValue() + ", " + -1 + ", " + -1 + ", '" + UUID.randomUUID() + "', " + weekNumber.getWeekNumber() + ")";

                    jdbcTemplate.execute(sql);
                }

                betAndValues.clear();
            }
        }
    }

    public void gradeBets(Integer week) {
        String sql = "SELECT id, gameid, bettype, betvalue FROM bets WHERE week=" + week + " AND betresult=" + -1  + " ORDER BY gameid ASC";

        List<UUID> gameIndex = new ArrayList<>();
        List<Bet> rawBets = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.add(UUID.fromString(resultSet.getString("gameid")));
            return new Bet(
                    UUID.fromString(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("gameid")),
                    Integer.parseInt(resultSet.getString("betvalue")),
                    Integer.parseInt(resultSet.getString("bettype")));
        });

        sql = "SELECT * FROM games WHERE week=" + week + " AND homeresult IS NOT NULL AND sphome IS NOT NULL";

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

    public List<UserBet> showWeekBets(int week) {
        String sql = "SELECT userid, gameId, betValue, betType, betResult, totalWon FROM bets WHERE week =" + week + " ORDER BY userid";

        List<Bet> allBets = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Bet(
                    Integer.parseInt(resultSet.getString("userid")),
                    UUID.fromString(resultSet.getString("gameId")),
                    Integer.parseInt(resultSet.getString("betValue")),
                    Integer.parseInt(resultSet.getString("betType")),
                    Integer.parseInt(resultSet.getString("betResult")),
                    Double.parseDouble(resultSet.getString("totalWon")));
        });


        sql = "SELECT * FROM teams";
        Map<UUID, Integer> teamIndex = new HashMap<UUID, Integer>();
        List<Team> teamList = jdbcTemplate.query(sql, (resultSet, i) -> {
            teamIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new Team(
                    resultSet.getString("name"),
                    resultSet.getString("abb"));
        });

        sql = "SELECT * FROM games WHERE week=" + week;
        Map<UUID, Integer> gameIndex = new HashMap<UUID, Integer>();
        List<GameLine> gameList = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("home")),
                    UUID.fromString(resultSet.getString("away")),
                    resultSet.getString("sphome") != null ? Double.parseDouble(resultSet.getString("sphome")) : 0,
                    resultSet.getString("spaway") != null ? Double.parseDouble(resultSet.getString("spaway")) : 0,
                    resultSet.getString("sphomeodds") != null ? Integer.parseInt(resultSet.getString("sphomeodds")) : 0,
                    resultSet.getString("spawayodds") != null ? Integer.parseInt(resultSet.getString("spawayodds")) : 0,
                    resultSet.getString("mlhome") != null ? Integer.parseInt(resultSet.getString("mlhome")) : 0,
                    resultSet.getString("mlaway") != null ? Integer.parseInt(resultSet.getString("mlaway")) : 0,
                    resultSet.getString("totalover") != null ? Double.parseDouble(resultSet.getString("totalover")) : 0,
                    resultSet.getString("totalunder") != null ? Double.parseDouble(resultSet.getString("totalunder")) : 0,
                    resultSet.getString("overodds") != null ? Integer.parseInt(resultSet.getString("overodds")) : 0,
                    resultSet.getString("underodds") != null ? Integer.parseInt(resultSet.getString("underodds")) : 0,
                    resultSet.getString("homeresult") != null ? Integer.parseInt(resultSet.getString("homeresult")) : 0,
                    resultSet.getString("awayresult") != null ? Integer.parseInt(resultSet.getString("awayresult")) : 0);
        });

        for (Bet bet : allBets) {
            Integer betGameIndex = gameIndex.get(bet.getGameId());
            bet.setGameLine(gameList.get(betGameIndex));
            bet.setTeam1(teamList.get(teamIndex.get(gameList.get(betGameIndex).getTeam1UUID())));
            bet.setTeam2(teamList.get(teamIndex.get(gameList.get(betGameIndex).getTeam2UUID())));
        }

        List<UserBet> userBetList = new ArrayList<UserBet>();

        Set<Integer> users = new HashSet<Integer>();
        for (Bet bet : allBets) {
            if(!users.contains(bet.getUserId())) {
                users.add(bet.getUserId());

                sql = "SELECT username FROM users WHERE id=?";

                String username = (String) jdbcTemplate.queryForObject(
                sql, new Object[] { bet.getUserId() }, String.class);

                userBetList.add(new UserBet(username, new ArrayList<Bet>()));
            }

            userBetList.get(userBetList.size() - 1).getBetList().add(bet);
        }

        return userBetList;
    }

    public List<WeekBet> showUserBets(String user) {
        String sql = "SELECT id FROM users WHERE username=?";

        Integer userId = (Integer) jdbcTemplate.queryForObject(
                sql, new Object[] { user }, Integer.class);

        sql = "SELECT userid, gameId, betValue, betType, betResult, totalWon, week FROM bets WHERE userid =" + userId + " ORDER BY week";

        List<Bet> allBets = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Bet(
                    Integer.parseInt(resultSet.getString("userid")),
                    UUID.fromString(resultSet.getString("gameId")),
                    Integer.parseInt(resultSet.getString("betValue")),
                    Integer.parseInt(resultSet.getString("betType")),
                    Integer.parseInt(resultSet.getString("betResult")),
                    Double.parseDouble(resultSet.getString("totalWon")),
                    Integer.parseInt(resultSet.getString("week")));
        });


        sql = "SELECT * FROM teams";
        Map<UUID, Integer> teamIndex = new HashMap<UUID, Integer>();
        List<Team> teamList = jdbcTemplate.query(sql, (resultSet, i) -> {
            teamIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new Team(
                    resultSet.getString("name"),
                    resultSet.getString("abb"));
        });

        sql = "SELECT * FROM games";
        Map<UUID, Integer> gameIndex = new HashMap<UUID, Integer>();
        List<GameLine> gameList = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("home")),
                    UUID.fromString(resultSet.getString("away")),
                    resultSet.getString("sphome") != null ? Double.parseDouble(resultSet.getString("sphome")) : 0,
                    resultSet.getString("spaway") != null ? Double.parseDouble(resultSet.getString("spaway")) : 0,
                    resultSet.getString("sphomeodds") != null ? Integer.parseInt(resultSet.getString("sphomeodds")) : 0,
                    resultSet.getString("spawayodds") != null ? Integer.parseInt(resultSet.getString("spawayodds")) : 0,
                    resultSet.getString("mlhome") != null ? Integer.parseInt(resultSet.getString("mlhome")) : 0,
                    resultSet.getString("mlaway") != null ? Integer.parseInt(resultSet.getString("mlaway")) : 0,
                    resultSet.getString("totalover") != null ? Double.parseDouble(resultSet.getString("totalover")) : 0,
                    resultSet.getString("totalunder") != null ? Double.parseDouble(resultSet.getString("totalunder")) : 0,
                    resultSet.getString("overodds") != null ? Integer.parseInt(resultSet.getString("overodds")) : 0,
                    resultSet.getString("underodds") != null ? Integer.parseInt(resultSet.getString("underodds")) : 0,
                    resultSet.getString("homeresult") != null ? Integer.parseInt(resultSet.getString("homeresult")) : 0,
                    resultSet.getString("awayresult") != null ? Integer.parseInt(resultSet.getString("awayresult")) : 0);
        });

        for (Bet bet : allBets) {
            Integer betGameIndex = gameIndex.get(bet.getGameId());
            bet.setGameLine(gameList.get(betGameIndex));
            bet.setTeam1(teamList.get(teamIndex.get(gameList.get(betGameIndex).getTeam1UUID())));
            bet.setTeam2(teamList.get(teamIndex.get(gameList.get(betGameIndex).getTeam2UUID())));
        }

        List<WeekBet> weekBetList = new ArrayList<WeekBet>();

        Set<Integer> weeks = new HashSet<Integer>();
        for (Bet bet : allBets) {
            if(!weeks.contains(bet.getWeek())) {
                weeks.add(bet.getWeek());

                weekBetList.add(new WeekBet(bet.getWeek(), new ArrayList<Bet>()));
            }

            weekBetList.get(weekBetList.size() - 1).getBetList().add(bet);
        }

        return weekBetList;
    }
}