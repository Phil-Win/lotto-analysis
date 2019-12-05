
package phil.win.lottoanalysis.model.raw.odds;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
@SuppressWarnings("unused")
public class Data {

    private List<GetCMSGameCategory> getCMSGameCategories;
    private List<GetCMSGamePlayStyle> getCMSGamePlayStyles;
    private List<GetCMSGameTheme> getCMSGameThemes;
    private List<GetCMSGameTicketPrice> getCMSGameTicketPrices;
    private List<GetCMSGameTopPrize> getCMSGameTopPrizes;
    private List<GetCMSGame> getCMSGames;
    private List<GetRetailerType> getRetailerTypes;

}
