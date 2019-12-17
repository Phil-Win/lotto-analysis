package phil.win.lottoanalysis.model.transformed.basic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lotto {

    private Long  id;
    private String  name;
    private String  imageUrl;
    private Long    cost;
    private Long    initialNumberOfTicketsAboveNTickets;
    private Long    remainingNumberOfTicketsAboveNTickets;
    private Long    estimatedInitialTotalNumberTickets;
    private Long    estimatedRemainingTotalNumberTickets;
}
