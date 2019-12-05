package phil.win.lottoanalysis.model.transformed.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Log4j2
public class Odds {
    private Double winningRatio;
    private Double losingRatio;
    private Double totalRatio;

    public Odds(String oddsAsString) {
        transformStringToOdds(oddsAsString);
    }

    protected void transformStringToOdds(String oddsAsString) {
        String regex    =   "(^\\d*\\.\\d+|\\d+.\\d*|\\d+) in (^\\d*\\.\\d+|\\d+.\\d*|\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher m   =   pattern.matcher(oddsAsString);
        if (m.find()) {
            log.debug("Found value! : {}", m.group(0));
            log.debug("Found value! : {}", m.group(1));
            log.debug("Found value! : {}", m.group(2));
            this.winningRatio   =   Double.valueOf(m.group(1));
            this.losingRatio    =   Double.valueOf(m.group(2));
            log.debug("Sum of losing and winning values: {}", Double.sum(winningRatio, losingRatio));
            this.totalRatio     =   Double.sum(winningRatio, losingRatio);
        } else {
            log.debug("No Match");
        }
    }
}
