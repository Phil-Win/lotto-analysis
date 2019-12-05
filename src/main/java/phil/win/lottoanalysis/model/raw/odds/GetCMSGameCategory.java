
package phil.win.lottoanalysis.model.raw.odds;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class GetCMSGameCategory {

    private String identifier;
    private String logoUrl;
    private String name;
    private List<String> nameAliases;
    private List<WebBanner> webBanners;
    private Object winUpTo;

}
