
package phil.win.lottoanalysis.model.raw.odds;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class GetCMSGame {

    private Boolean canBuyInStore;
    private Boolean canPlayOnline;
    private String dateAdded;
    private String displayedTicketPrice;
    private String displayedTopPrize;
    private Object fastCashJackpotPayoutCopy;
    private String gameCategoryIdentifier;
    private Long gamesLobbyOrder;
    private Boolean hasSecondChance;
    private String identifier;
    private Object igtId;
    private Boolean isFeatured;
    private Boolean isInstantGame;
    private Boolean isKeno;
    private Boolean isMultiTicket;
    private Object jackpot;
    private Boolean limitedAvailability;
    private String logoUrl;
    private String name;
    private Object nameAliases;
    private Long neoGamesId;
    private String overallOdds;
    private List<PlayStyle> playStyles;
    private Object raffleData;
    private Object ribbon;
    private List<Theme> themes;
    private List<TicketPrice> ticketPrices;
    private List<TopPrize> topPrizes;
    private List<WebBanner> webBanners;

}
