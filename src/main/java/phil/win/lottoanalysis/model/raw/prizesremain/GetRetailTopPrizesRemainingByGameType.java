
package phil.win.lottoanalysis.model.raw.prizesremain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class GetRetailTopPrizesRemainingByGameType {

    @JsonProperty("cms_game_igt_id")
    private Long cmsGameIgtId;
    @JsonProperty("game_name")
    private String gameName;
    private List<PrizesRemainingDatum> prizesRemainingData;

}
