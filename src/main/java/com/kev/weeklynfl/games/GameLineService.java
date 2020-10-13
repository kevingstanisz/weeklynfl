package com.kev.weeklynfl.games;

import com.kev.weeklynfl.games.lines.ScrapeBetOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameLineService {
    private final GameRepository gameRepository;

    @Autowired
    public GameLineService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void saveGameLines() {
        ScrapeBetOnline scrapeBetOnline = new ScrapeBetOnline();
        List<GameLine> gameLines = scrapeBetOnline.getLines();



    }



}
