package phil.win.lottoanalysis.model.transformed.expectedvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValueRow {
    private TicketCombinations  ticketCombinationCurrent;
    private TicketCombinations  ticketCombinationInitial;

    public BigDecimal  getOddsChange() {
        try {
            return ticketCombinationCurrent.getProbability().divide(ticketCombinationInitial.getProbability(), 200, RoundingMode.HALF_DOWN);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getAverageDifference() {
        return ticketCombinationCurrent.getAverageTicketValue().subtract(ticketCombinationInitial.getAverageTicketValue());
    }
}
