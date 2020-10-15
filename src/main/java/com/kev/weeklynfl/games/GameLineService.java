package com.kev.weeklynfl.games;

import com.kev.weeklynfl.games.lines.ScrapeBetOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class GameLineService {
    private final JdbcTemplate jdbcTemplate;
    private final GameRepository gameRepository;

    @Autowired
    public GameLineService(JdbcTemplate jdbcTemplate, GameRepository gameRepository) {
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
            String sql = "SELECT id, home, away FROM games WHERE (home='" + teamUUID.get(gameLine.getTeam1()) + "' OR home='" + teamUUID.get(gameLine.getTeam2()) + "') AND week = 6";

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



}
