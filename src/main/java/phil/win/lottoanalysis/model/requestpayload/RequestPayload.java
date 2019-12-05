
package phil.win.lottoanalysis.model.requestpayload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
@SuppressWarnings("unused")
public class RequestPayload {

    private Object operation;
    private String query;
    private Object variables;

}
