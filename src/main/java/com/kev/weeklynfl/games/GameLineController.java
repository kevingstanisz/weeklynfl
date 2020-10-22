package com.kev.weeklynfl.games;

import com.kev.weeklynfl.bets.Bet;
import com.kev.weeklynfl.bets.BetService;
import com.kev.weeklynfl.games.showgames.ListGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void saveGameResults(){ gameService.getResults();
    }

    @PostMapping(path = "savebets")
    public void saveBets(@RequestBody List<GameLine> gameLines){
        betService.saveBets(gameLines);
    }

    @GetMapping
    public List<GameLine> selectWeeklyGames(){
        return listGamesService.selectWeeklyGames();
    }

    @GetMapping(path = "{id}")
    public List<GameLine> selectWeeklyBetGames(@PathVariable("id") int id){
        return listGamesService.selectWeeklyBetGames(id);
    }

//    @PutMapping
//    public void saveGameLines() {
//        gameLineService.saveGameLines();
//    }

}
