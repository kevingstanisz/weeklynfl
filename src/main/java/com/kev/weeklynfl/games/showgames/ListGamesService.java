package com.kev.weeklynfl.games.showgames;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.games.GameLine;
import com.kev.weeklynfl.games.WeekNumber;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class ListGamesService {
    private final JdbcTemplate jdbcTemplate;

    public ListGamesService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GameLine> selectWeeklyGames() {
        WeekNumber weekNumber = new WeekNumber();
        String sql = "SELECT * FROM games WHERE week=" + weekNumber.getWeekNumber() + " ORDER BY scheduled";

        Map<UUID, String> teamUUID = getTeamFromUUID();

        List<GameLine> gameLines = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    teamUUID.get(UUID.fromString(resultSet.getString("home"))),
                    teamUUID.get(UUID.fromString(resultSet.getString("away"))),
                    resultSet.getString("sphome") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("sphome")),
                    resultSet.getString("spaway") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("spaway")),
                    resultSet.getString("sphomeodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("sphomeodds")),
                    resultSet.getString("spawayodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("spawayodds")),
                    resultSet.getString("mlhome") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("mlhome")),
                    resultSet.getString("mlaway") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("mlaway")),
                    resultSet.getString("totalover") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("totalover")),
                    resultSet.getString("totalunder") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("totalunder")),
                    resultSet.getString("overodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("overodds")),
                    resultSet.getString("underodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("underodds")));
        });

        return gameLines;
    }

    public List<GameLine> selectWeeklyBetGames(int id) {
        WeekNumber weekNumber = new WeekNumber();
        String sql = "SELECT * FROM games WHERE week=" + weekNumber.getWeekNumber() + " ORDER BY scheduled";

        Map<UUID, String> teamUUID = getTeamFromUUID();
        Map<UUID, Integer> gameIndex = new HashMap<UUID, Integer>();

        List<GameLine> gameLines = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    teamUUID.get(UUID.fromString(resultSet.getString("home"))),
                    teamUUID.get(UUID.fromString(resultSet.getString("away"))),
                    resultSet.getString("sphome") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("sphome")),
                    resultSet.getString("spaway") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("spaway")),
                    resultSet.getString("sphomeodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("sphomeodds")),
                    resultSet.getString("spawayodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("spawayodds")),
                    resultSet.getString("mlhome") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("mlhome")),
                    resultSet.getString("mlaway") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("mlaway")),
                    resultSet.getString("totalover") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("totalover")),
                    resultSet.getString("totalunder") == null ? Double.MAX_VALUE : Double.parseDouble(resultSet.getString("totalunder")),
                    resultSet.getString("overodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("overodds")),
                    resultSet.getString("underodds") == null ? Integer.MAX_VALUE : Integer.parseInt(resultSet.getString("underodds")));
        });

        UUID testUUID = UUID.fromString("62753844-3272-4867-b1b5-ebd19c525a80");

        sql = "SELECT id, gameid, bettype, betvalue FROM bets WHERE week=" + weekNumber.getWeekNumber() + " AND userid='" + testUUID  + "'";

        AtomicReference<Integer> betType = new AtomicReference<>(0);
        AtomicReference<Integer> betValue = new AtomicReference<>(0);

        List<Bet> bets = jdbcTemplate.query(sql, (resultSet, i) -> {
            betType.set(Integer.parseInt(resultSet.getString("bettype")));
            betValue.set(Integer.parseInt(resultSet.getString("betvalue")));
            return new Bet(
                    Integer.parseInt(resultSet.getString("id")),
                    UUID.fromString(resultSet.getString("gameid")),
                    betType.get() == 1,
                    betType.get() == 1 ? betValue.get() : 0,
                    betType.get() == 2,
                    betType.get() == 2 ? betValue.get() : 0,
                    betType.get() == 3,
                    betType.get() == 3 ? betValue.get() : 0,
                    betType.get() == 4,
                    betType.get() == 4 ? betValue.get() : 0,
                    betType.get() == 5,
                    betType.get() == 5 ? betValue.get() : 0,
                    betType.get() == 6,
                    betType.get() == 6 ? betValue.get() : 0);
        });

        for (Bet bet : bets) {
            gameLines.get(gameIndex.get(bet.getGameId())).setBets(bet);
        }

        return gameLines;
    }

    private Map<UUID, String> getTeamFromUUID() {
        HashMap<UUID, String> teamUUID = new HashMap<UUID, String>();

        jdbcTemplate.query("SELECT * FROM teams", new ResultSetExtractor<Map>() {
            @Override
            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    teamUUID.put(UUID.fromString(rs.getString("id")), rs.getString("name"));
                }
                return teamUUID;
            }
        });

        return teamUUID;
    }
}