package phil.win.lottoanalysis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import phil.win.lottoanalysis.model.raw.odds.GetCMSGame;
import phil.win.lottoanalysis.model.raw.odds.InstantGamemsOddsResponse;
import phil.win.lottoanalysis.model.raw.prizesremain.Data;
import phil.win.lottoanalysis.model.raw.prizesremain.GetRetailTopPrizesRemainingByGameType;
import phil.win.lottoanalysis.model.raw.prizesremain.InstantGamePrizesRemainingResponse;
import phil.win.lottoanalysis.model.requestpayload.RequestPayload;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.basic.Lotto;
import phil.win.lottoanalysis.processor.TransformRawDataUtil;
import phil.win.lottoanalysis.processor.TransformedGames;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MichiganRemainingPrizesService {

    @Autowired
    TransformedGames transformedGames;

    private String REQUEST_URL_AS_STRING    =   "https://www.michiganlottery.com/api";

    public Data getRawPrizesRemaining() {
        InstantGamePrizesRemainingResponse  instantGamePrizesRemainingResponse  =   null;
        RequestPayload requestPayload;
        RestTemplate restTemplate    =   new RestTemplate();
        ObjectMapper	mapper	=	new ObjectMapper();
        try {
            requestPayload	=	mapper.readValue(new File("src/main/resources/RequestPayloadRemainingPrizes.json"), RequestPayload.class);
            instantGamePrizesRemainingResponse  =   restTemplate.postForObject(new URI(REQUEST_URL_AS_STRING), requestPayload, InstantGamePrizesRemainingResponse.class);
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
        return instantGamePrizesRemainingResponse.getData();
    }

    public List<GetCMSGame> getRawOdds() {
        InstantGamemsOddsResponse instantGamemsOddsResponse  =   null;
        RequestPayload requestPayload;
        RestTemplate restTemplate    =   new RestTemplate();
        ObjectMapper	mapper	=	new ObjectMapper();
        try {
            requestPayload	=	mapper.readValue(new File("src/main/resources/RequestPayloadOdds.json"), RequestPayload.class);
            instantGamemsOddsResponse  =   restTemplate.postForObject(new URI(REQUEST_URL_AS_STRING), requestPayload, InstantGamemsOddsResponse.class);
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
        return instantGamemsOddsResponse.getData().getGetCMSGames();
    }

    public Boolean transformRawData(Data rawData, List<GetCMSGame> rawOddsAndPrice) {
        List<Game>  listOfGames =   new ArrayList<Game>();
        Game    gameOfInterest;
        GetCMSGame  gameOddsOfInterest;
        String      oddsOfInterestAsString;
        Long      ticketCostOfInterest;

        for (GetRetailTopPrizesRemainingByGameType game : rawData.getGetRetailTopPrizesRemainingByGameType()) {
            gameOfInterest  =   new Game();
            gameOfInterest.setId(game.getCmsGameIgtId());
            gameOfInterest.setName(game.getGameName());
            gameOfInterest.setProcessedDate(new Date());

            gameOddsOfInterest  =   TransformRawDataUtil.getCorrectCMSGame(gameOfInterest.getId(), rawOddsAndPrice);
            if (gameOddsOfInterest == null) {
                log.debug("Odds and ticket price query could not be found for the GameID:Name of {}:{}", gameOfInterest.getId(), gameOfInterest.getName());
                continue;
            }
            ticketCostOfInterest    =   TransformRawDataUtil.transformTicketPriceStringToBigDecimal(gameOddsOfInterest.getDisplayedTicketPrice()).longValue();
            oddsOfInterestAsString  =   gameOddsOfInterest.getOverallOdds();
            gameOfInterest.setCost(ticketCostOfInterest);
            gameOfInterest.setImageUrl(gameOddsOfInterest.getLogoUrl());
            gameOfInterest.setEstimatedInitialTotalNumberTickets(TransformRawDataUtil.getInitialTicketCount(game, oddsOfInterestAsString));
            gameOfInterest.setEstimatedRemainingTotalNumberTickets(TransformRawDataUtil.getRemainingTicketCount(game, oddsOfInterestAsString));
            gameOfInterest.setRemainingWinningTicketCount(TransformRawDataUtil.getRemainingPrizeCount(game));
            gameOfInterest.setInitialWinningTicketCount(TransformRawDataUtil.getInitialPrizeCount(game));
            gameOfInterest.setRemainingPrizes(TransformRawDataUtil.getListOfRemainingPrizes(game, oddsOfInterestAsString));
            gameOfInterest.setInitialPrizes(TransformRawDataUtil.getListOfInitialPrizes(game, oddsOfInterestAsString));
            listOfGames.add(gameOfInterest);
        }
        transformedGames.setGameList(listOfGames);
        return true;
    }


}
