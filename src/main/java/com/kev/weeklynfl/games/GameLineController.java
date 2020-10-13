package com.kev.weeklynfl.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/save-game-lines")
public class GameLineController {

    private final GameLineService gameLineService;

    @Autowired
    public GameLineController(GameLineService gameLineService) {
        this.gameLineService = gameLineService;
    }

//    @PutMapping
//    public void saveGameLines() {
//        gameLineService.saveGameLines();
//    }

}
