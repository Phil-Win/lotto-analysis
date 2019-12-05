package phil.win.lottoanalysis.model.transformed.expectedvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpectedValue {
    private BigInteger highValue;
    private BigInteger lowValue;
    private TicketCombinations  current;
    private TicketCombinations  initial;
}
