
package phil.win.lottoanalysis.model.raw.odds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class GetCMSGameTopPrize {

    private String identifier;
    private String prizeRange;

}
