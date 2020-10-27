package com.kev.weeklynfl.games;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.games.lines.ScrapeBetOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

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

    public void getResults() throws Exception {
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
                .uri(URI.create("http://api.sportradar.us/nfl/official/trial/v6/en/games/2020/REG/" + weekNumber.getWeekNumber() + "/schedule.json?api_key=9pgtwyez27zssnpce7vv7967"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Object obj = new JSONParser().parse(response.body());
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject weekObject = (JSONObject) jsonObject.get("week");
        JSONArray gameArray = (JSONArray) weekObject.get("games");

        for (int i = 0, size = gameArray.size(); i < size; i++)
        {
            JSONObject game = (JSONObject) gameArray.get(i);

            if(game.get("status").toString().equals("closed")) {

                UUID id = UUID.fromString((String) game.get("id"));

                JSONObject scores = (JSONObject) game.get("scoring");
                Integer resultHome = Integer.parseInt(scores.get("home_points").toString());
                Integer resultAway = Integer.parseInt(scores.get("away_points").toString());

                if (gameIndex.get(id) != null) {
                    String sqlUpdate = "UPDATE games SET homeresult = " + resultHome + " , awayresult = " + resultAway + " WHERE id='" + id + "'";
                    jdbcTemplate.execute(sqlUpdate);
                }
            }
        }
    }
}
