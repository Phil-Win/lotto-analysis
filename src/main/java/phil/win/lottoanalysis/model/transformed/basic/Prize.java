package phil.win.lottoanalysis.model.transformed.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prize {
    private Long prize;
    private Long ticketCount;
}
