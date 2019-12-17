package phil.win.lottoanalysis.model.transformed.expectedvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValueTable {

    public List<ValueRow> values;
    public BigDecimal   initialExpectedValue;
    public BigDecimal   currentExpectedValue;

    public BigDecimal   getExpectedValuePercentIncrease() {
        if (currentExpectedValue.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        } else {
            return currentExpectedValue.divide(initialExpectedValue, 200, RoundingMode.HALF_DOWN);
        }
    }
}
