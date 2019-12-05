package phil.win.lottoanalysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phil.win.lottoanalysis.combinatorics.ExpectedValueCalculator;
import phil.win.lottoanalysis.model.raw.odds.GetCMSGame;
import phil.win.lottoanalysis.model.raw.prizesremain.Data;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ExpectedValue;
import phil.win.lottoanalysis.processor.TransformedGames;
import phil.win.lottoanalysis.service.MichiganRemainingPrizesService;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transformed")
public class LottoTransformedApi {

    @Autowired
    MichiganRemainingPrizesService michiganRemainingPrizesService;

    @Autowired
    TransformedGames transformedGames;

    @GetMapping("/update")
    public ResponseEntity<Boolean> getGames() {
        Data rawData    =   michiganRemainingPrizesService.getRawPrizesRemaining();
        List<GetCMSGame> rawOddsAndPrice    =   michiganRemainingPrizesService.getRawOdds();
        return ResponseEntity.ok().body(michiganRemainingPrizesService.transformRawData(rawData, rawOddsAndPrice));
    }

    @GetMapping("/combinations")
    public ResponseEntity<ExpectedValue> getCombinations(@RequestParam Integer low, @RequestParam Integer high,
                                                         @RequestParam Integer gameid, @RequestParam Integer ticketspurchased) {
        Game gameOfInterest = null;
        ExpectedValue   expectedValue;

        if (transformedGames.getGameList() == null) {
            Data rawData    =   michiganRemainingPrizesService.getRawPrizesRemaining();
            List<GetCMSGame> rawOddsAndPrice    =   michiganRemainingPrizesService.getRawOdds();
            michiganRemainingPrizesService.transformRawData(rawData, rawOddsAndPrice);
        }
        for (Game game : transformedGames.getGameList()) {
            if (game.getId().equals(gameid.longValue())) {
                gameOfInterest  =   game;
                break;
            }
        }


        if (gameOfInterest == null) {
            return ResponseEntity.notFound().build();
        } else {
            ExpectedValueCalculator expectedValueCalculator =   new ExpectedValueCalculator(gameOfInterest, ticketspurchased.longValue());
            expectedValue   =   expectedValueCalculator.determineRangeCombinatorics(new BigInteger(String.valueOf(low)), new BigInteger(String.valueOf(high)));
            return ResponseEntity.ok(expectedValue);
        }
    }

}
