package phil.win.lottoanalysis.processor;


import org.springframework.context.annotation.Configuration;
import phil.win.lottoanalysis.model.transformed.basic.Game;

import java.util.List;

@Configuration
public class TransformedGames {
    public List<Game> gameList;

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
}
