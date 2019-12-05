
package phil.win.lottoanalysis.model.raw.prizesremain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class PrizesRemainingDatum {

    @JsonProperty("prize_amount")
    private Long prizeAmount;
    @JsonProperty("prize_level")
    private Long prizeLevel;
    @JsonProperty("prizes_remaining")
    private Long prizesRemaining;
    @JsonProperty("starting_amount")
    private Long startingAmount;

}
