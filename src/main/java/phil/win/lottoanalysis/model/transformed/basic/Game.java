package phil.win.lottoanalysis.model.transformed.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {

    private Long  id;
    private String  name;
    private Date    processedDate;
    private Long    cost;
    private Long    estimatedInitialTotalNumberTickets;
    private Long    estimatedRemainingTotalNumberTickets;
    private Long    estimatedTicketsSoldSincePreviousDay;
    private Long    remainingWinningTicketCount;
    private Long    initialWinningTicketCount;
    private List<Prize> remainingPrizes;
    private List<Prize> initialPrizes;
}
