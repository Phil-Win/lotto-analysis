package phil.win.lottoanalysis.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import phil.win.lottoanalysis.processor.TransformedGames;

@Component
public class DailyGameDataRefresher {

    @Autowired
    TransformedGames transformedGames;

    @Scheduled(cron = "0 0 * * * *")
    public void resetTransformedGames() {
        transformedGames.setGameList();
    }

}
