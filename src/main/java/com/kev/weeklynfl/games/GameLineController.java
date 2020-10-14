package com.kev.weeklynfl.games;

import com.kev.weeklynfl.games.showgames.ListGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/games")
public class GameLineController {

    private final GameLineService gameLineService;
    private final ListGamesService listGamesService;


    @Autowired
    public GameLineController(GameLineService gameLineService, ListGamesService listGamesService) {
        this.gameLineService = gameLineService;
        this.listGamesService = listGamesService;
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
