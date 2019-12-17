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
public class TicketCombinations {
    private BigInteger highValue;
    private BigInteger lowValue;
    private BigDecimal      averageTicketValue;
    private BigInteger  waysToGetScenario;
    private BigInteger  totalCombinations;

    public String  getStringProbability() {
        return waysToGetScenario.toString() + " out of " + totalCombinations.toString();
    }

    public BigDecimal  getProbability() {
        if (totalCombinations.equals(BigInteger.ZERO)) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(waysToGetScenario).divide(new BigDecimal(totalCombinations), 200, RoundingMode.HALF_DOWN);
        }
    }
}
