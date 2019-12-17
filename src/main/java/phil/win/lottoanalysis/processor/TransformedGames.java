package phil.win.lottoanalysis.processor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import phil.win.lottoanalysis.model.raw.odds.GetCMSGame;
import phil.win.lottoanalysis.model.raw.prizesremain.Data;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.basic.Lotto;
import phil.win.lottoanalysis.service.MichiganRemainingPrizesService;

import java.util.List;

@Configuration
public class TransformedGames {

    @Autowired
    MichiganRemainingPrizesService michiganRemainingPrizesService;

    public List<Game> gameList;


    public List<Game> getGameList() {
        if (this.gameList   == null) {
            setGameList();
        }
        return gameList;
    }

    public Game getGameById(long id) {
        if (this.gameList   == null) {
            setGameList();
        }
        for (Game game : this.gameList) {
            if (game.getId() == id) {
                return game;
            }
        }
        return null;
    }

    public void setGameList() {
        this.gameList   =   null;
        Data rawData    =   michiganRemainingPrizesService.getRawPrizesRemaining();
        List<GetCMSGame> rawOddsAndPrice    =   michiganRemainingPrizesService.getRawOdds();
        this.michiganRemainingPrizesService.transformRawData(rawData, rawOddsAndPrice);
    }


    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

}
