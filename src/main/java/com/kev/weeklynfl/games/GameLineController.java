package com.kev.weeklynfl.games;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.bets.BetService;
import com.kev.weeklynfl.bets.UserBet;
import com.kev.weeklynfl.bets.WeekBet;
import com.kev.weeklynfl.games.showgames.ListGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/games")
@CrossOrigin("http://localhost:3000")
public class GameLineController {

    private final GameService gameService;
    private final ListGamesService listGamesService;
    private final BetService betService;


    @Autowired
    public GameLineController(GameService gameService, ListGamesService listGamesService, BetService betService) {
        this.gameService = gameService;
        this.listGamesService = listGamesService;
        this.betService = betService;
    }

    @PostMapping(path = "savelines")
    public void saveGameLines(){
        gameService.saveGameLines();
    }

    @PostMapping(path = "getresults")
    public void saveGameResults(@RequestBody WeekNumber week){
        try {
            gameService.getResults(week.getWeekNumberFromFrontend());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path = "gradebets")
    public void gradeBets(@RequestBody WeekNumber week){
        betService.gradeBets(week.getWeekNumberFromFrontend());
    }

    @PostMapping(path = "savebets")
    public void saveBets(@RequestBody List<GameLine> gameLines,  @RequestHeader("Authorization") String authToken){
        betService.saveBets(gameLines, authToken);
    }

    @GetMapping
    public List<GameLine> selectWeeklyGames(){
        return listGamesService.selectWeeklyGames();
    }

    @GetMapping(path = "{id}")
    public List<GameLine> selectWeeklyBetGames(@PathVariable("id") int id, @RequestHeader("Authorization") String authToken){
        return listGamesService.selectWeeklyBetGames(id, authToken);
    }

     @GetMapping(path = "bets/week/{week}")
     public List<UserBet> selectAllWeeklyBetGames(@PathVariable("week") int week){
         return betService.showWeekBets(week);
     }

    @GetMapping(path = "bets/user/{user}")
    public List<WeekBet> selectAllWeeklyBetGames(@PathVariable("user") String user) {
        return betService.showUserBets(user);
    }

//    @PutMapping
//    public void saveGameLines() {
//        gameLineService.saveGameLines();
//    }

}
