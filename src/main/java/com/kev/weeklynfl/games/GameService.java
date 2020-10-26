package com.kev.weeklynfl.games;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.games.lines.ScrapeBetOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GameService {
    private final JdbcTemplate jdbcTemplate;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(JdbcTemplate jdbcTemplate, GameRepository gameRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.gameRepository = gameRepository;
    }


    public void saveGameLines() {
        WeekNumber weekNumber = new WeekNumber();
        ScrapeBetOnline scrapeBetOnline = new ScrapeBetOnline();
        List<GameLine> gameLines = scrapeBetOnline.getLines();
        Map<String, UUID> teamUUID = getUUIDFromTeam();


        // List<GameLine> identifiedGames = new ArrayList<GameLine>();

        for (GameLine gameLine : gameLines) {
            String sql = "SELECT id, home, away FROM games WHERE (home='" + teamUUID.get(gameLine.getTeam1()) + "' OR home='" + teamUUID.get(gameLine.getTeam2()) + "') AND week = " + weekNumber.getWeekNumber();

            List<UUID> gameId = jdbcTemplate.query(sql, (resultSet, i) -> {
                return UUID.fromString(resultSet.getString("id"));
            });

            if(!gameId.isEmpty()) {
                gameLine.setId(gameId.get(0));
            }
        }

        for(GameLine gameLine : gameLines) {
            if(gameLine.getId() != null) {
                System.out.println(gameLine.toString());

                jdbcTemplate.update(
                        "update games set " +
                                "sphome = ?, spaway = ?, sphomeodds = ?, spawayodds = ?, mlhome = ?, mlaway = ?, totalover = ?, totalunder = ?, overodds = ?, underodds = ? where id = ?",
                        gameLine.getSp2(), gameLine.getSp1(), gameLine.getSp2Odds(), gameLine.getSp1Odds(), gameLine.getMl2(), gameLine.getMl1(), gameLine.getOver(), gameLine.getUnder(), gameLine.getOverOdds(), gameLine.getUnderOdds(), gameLine.getId());
            }
        }
    }

    private Map<String, UUID> getUUIDFromTeam() {
        HashMap<String, UUID> teamUUID = new HashMap<String, UUID>();

        jdbcTemplate.query("SELECT * FROM teams", new ResultSetExtractor<Map>() {
            @Override
            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    teamUUID.put(rs.getString("name"), UUID.fromString(rs.getString("id")));
                }
                return teamUUID;
            }
        });

        return teamUUID;
    }

    public void getResults() {
        WeekNumber weekNumber = new WeekNumber();

        String sql = "SELECT id FROM games WHERE week=" + weekNumber.getWeekNumber();

        Map<UUID, Integer> gameIndex = new HashMap<UUID, Integer>();
        List<GameLine> gameLines = jdbcTemplate.query(sql, (resultSet, i) -> {
            gameIndex.put(UUID.fromString(resultSet.getString("id")), i);
            return new GameLine(
                    UUID.fromString(resultSet.getString("id")),
                    null,
                    null);
        });

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.sportradar.us/nfl/official/trial/v6/en/games/2020/REG/1/schedule.json?api_key=++++++++++++++"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(response.body());
    }
}
