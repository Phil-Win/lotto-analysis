package phil.win.lottoanalysis.model.transformed;

import org.junit.jupiter.api.Test;
import phil.win.lottoanalysis.model.transformed.basic.Odds;

import static org.junit.jupiter.api.Assertions.*;

class OddsTest {

    @Test
    void transformStringToOdds() {
        Double expectedWinners  = Double.valueOf(1);
        Double expectedLosers   =   Double.valueOf(4.64);
        Double expectedTotal    =   Double.valueOf(5.64);

        Odds actualOdds =   new Odds("1 in 4.64");
        assertEquals(expectedWinners, actualOdds.getWinningRatio());
        assertEquals(expectedLosers, actualOdds.getLosingRatio());
        assertEquals(expectedTotal, actualOdds.getTotalRatio());
    }
}