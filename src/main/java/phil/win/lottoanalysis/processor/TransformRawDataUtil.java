package phil.win.lottoanalysis.processor;

import lombok.extern.slf4j.Slf4j;
import phil.win.lottoanalysis.model.raw.odds.GetCMSGame;
import phil.win.lottoanalysis.model.raw.prizesremain.GetRetailTopPrizesRemainingByGameType;
import phil.win.lottoanalysis.model.raw.prizesremain.PrizesRemainingDatum;
import phil.win.lottoanalysis.model.transformed.basic.Odds;
import phil.win.lottoanalysis.model.transformed.basic.Prize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TransformRawDataUtil {

    public static Long getRemainingPrizeCount(GetRetailTopPrizesRemainingByGameType game) {
        Long count  = Long.valueOf(0);
        for (PrizesRemainingDatum prizesRemainingDatum : game.getPrizesRemainingData()) {
            count+= prizesRemainingDatum.getPrizesRemaining();
        }
        return count;
    }

    public static Long getRemainingTicketCount(GetRetailTopPrizesRemainingByGameType game, String odds) {
        Odds    gameOdds   =   new Odds(odds);
        Long    remainingPrizes =   getRemainingPrizeCount(game);

        return (Long)Math.round(remainingPrizes * gameOdds.getTotalRatio());
    }

    public static Long getInitialPrizeCount(GetRetailTopPrizesRemainingByGameType game) {
        Long count  = Long.valueOf(0);
        for (PrizesRemainingDatum prizesRemainingDatum : game.getPrizesRemainingData()) {
            count+= prizesRemainingDatum.getStartingAmount();
        }
        return count;
    }

    public static Long getInitialTicketCount(GetRetailTopPrizesRemainingByGameType game, String odds) {
        Odds gameOdds   =   new Odds(odds);
        Long    initialPrizes =   getInitialPrizeCount(game);

        return (Long)Math.round(initialPrizes * gameOdds.getTotalRatio());
    }

    public static List<Prize> getListOfRemainingPrizes(GetRetailTopPrizesRemainingByGameType game, String oddsAsString) {
        List<Prize> listOfPrizes    =   new ArrayList<Prize>();
        Prize   prizeOfInterest;
        for (PrizesRemainingDatum prizesRemainingDatum : game.getPrizesRemainingData()) {
            prizeOfInterest =   new Prize(prizesRemainingDatum.getPrizeAmount(), prizesRemainingDatum.getPrizesRemaining());
            listOfPrizes.add(prizeOfInterest);
        }
        listOfPrizes.add(new Prize(Long.valueOf(0), getRemainingTicketCount(game, oddsAsString) - getRemainingPrizeCount(game)));
        return listOfPrizes;
    }

    public static List<Prize> getListOfInitialPrizes(GetRetailTopPrizesRemainingByGameType game, String oddsAsString) {
        List<Prize> listOfPrizes    =   new ArrayList<Prize>();
        Prize   prizeOfInterest;
        for (PrizesRemainingDatum prizesRemainingDatum : game.getPrizesRemainingData()) {
            prizeOfInterest =   new Prize(prizesRemainingDatum.getPrizeAmount(), prizesRemainingDatum.getStartingAmount());
            listOfPrizes.add(prizeOfInterest);
        }
        listOfPrizes.add(new Prize(Long.valueOf(0), getInitialTicketCount(game, oddsAsString) - getInitialPrizeCount(game)));
        return listOfPrizes;
    }

    public static GetCMSGame getCorrectCMSGame(Long gameId, List<GetCMSGame> oddsQuery) {
        for (GetCMSGame game : oddsQuery) {
            log.debug("Getting the game ID vs the input: {} : {}", game.getIgtId(), gameId);
            if (game.getIgtId() == null) {
                continue;
            }
            if (Long.valueOf((Integer) game.getIgtId()).equals(gameId)) {
                log.debug("Game found! Returning the data from the GameID:Name - {}:{}", game.getIgtId(), game.getName());
                return game;
            }
        }
        log.debug("No CMSGame found... return null");
        return null;
    }

    public static BigDecimal transformTicketPriceStringToBigDecimal(String displayedTicketPrice) {
        return new BigDecimal(displayedTicketPrice.replace("$", ""));
    }
}
