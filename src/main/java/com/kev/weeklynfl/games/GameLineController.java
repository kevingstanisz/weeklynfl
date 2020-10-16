package com.kev.weeklynfl.games;

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


    @Autowired
    public GameLineController(GameService gameService, ListGamesService listGamesService) {
        this.gameService = gameService;
        this.listGamesService = listGamesService;
    }

    @PostMapping(path = "savelines")
    public void saveGameLines(){
        gameService.saveGameLines();
    }

    @PostMapping(path = "getresults")
    public void saveGameResults(){ gameService.getResults();
    }

    @GetMapping
    public List<GameLine> selectWeeklyGames(){
        return listGamesService.selectWeeklyGames();
    }



//    @PutMapping
//    public void saveGameLines() {
//        gameLineService.saveGameLines();
//    }

}
