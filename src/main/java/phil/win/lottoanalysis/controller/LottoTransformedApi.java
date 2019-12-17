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
import phil.win.lottoanalysis.model.transformed.basic.Lotto;
import phil.win.lottoanalysis.model.transformed.basic.Prize;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ValueRow;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ValueTable;
import phil.win.lottoanalysis.processor.TransformedGames;
import phil.win.lottoanalysis.service.MichiganRemainingPrizesService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transformed")
public class LottoTransformedApi {

    @Autowired
    MichiganRemainingPrizesService michiganRemainingPrizesService;

    @Autowired
    TransformedGames transformedGames;

    @GetMapping("/dollarrange")
    public ResponseEntity<ValueTable> dollarrange(@RequestParam Long range,
                                                    @RequestParam Integer gameid, @RequestParam Integer ticketspurchased) {
        Game gameOfInterest = transformedGames.getGameById(gameid);
        ExpectedValueCalculator expectedValueCalculator =   new ExpectedValueCalculator(gameOfInterest, ticketspurchased.longValue());


        if (gameOfInterest == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(expectedValueCalculator.getValueRowListDollarRange(range));
        }
    }

    @GetMapping("/percentrange")
    public ResponseEntity<ValueTable> getpercentrange(@RequestParam Double range,
                                                      @RequestParam Integer gameid, @RequestParam Integer ticketspurchased) {
        Game gameOfInterest = transformedGames.getGameById(gameid);
        ExpectedValueCalculator expectedValueCalculator =   new ExpectedValueCalculator(gameOfInterest, ticketspurchased.longValue());


        if (gameOfInterest == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(expectedValueCalculator.getValueRowListPercentRange(range));
        }
    }
    @GetMapping("/gamesinformation")
    public ResponseEntity<List<Lotto>> getGamesInformation(@RequestParam Integer tickets) {
        List<Lotto> lottoList   =   new ArrayList<Lotto>();
        Lotto lotto;
        Long count;
        Long lowerPrizeValue;

        for (Game game : transformedGames.getGameList()) {
            lotto   =   new Lotto();
            lotto.setName(game.getName());
            lotto.setId(game.getId());
            lotto.setImageUrl(game.getImageUrl());
            lotto.setCost(game.getCost());
            lotto.setEstimatedInitialTotalNumberTickets(game.getEstimatedInitialTotalNumberTickets());
            lotto.setEstimatedRemainingTotalNumberTickets(game.getEstimatedRemainingTotalNumberTickets());
            lowerPrizeValue  =   tickets * game.getCost();
            count = Long.valueOf("0");
            for (Prize prize : game.getRemainingPrizes()) {
                if (prize.getPrize() >= lowerPrizeValue) {
                    count   =   count + prize.getTicketCount();
                }
            }
            lotto.setRemainingNumberOfTicketsAboveNTickets(count);
            count = Long.valueOf("0");
            for (Prize prize : game.getInitialPrizes()) {
                if (prize.getPrize() >= lowerPrizeValue) {
                    count   =   count + prize.getTicketCount();
                }
            }
            lotto.setInitialNumberOfTicketsAboveNTickets(count);
            lottoList.add(lotto);
        }
        return ResponseEntity.ok(lottoList);
    }

}
