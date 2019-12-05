package phil.win.lottoanalysis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phil.win.lottoanalysis.model.raw.odds.GetCMSGame;
import phil.win.lottoanalysis.model.raw.prizesremain.Data;
import phil.win.lottoanalysis.service.MichiganRemainingPrizesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/raw")
public class LottoRawApi {

    @Autowired
    MichiganRemainingPrizesService michiganRemainingPrizesService;

    private ObjectMapper    mapper  =   new ObjectMapper();

    @GetMapping("/odds")
    public ResponseEntity<List<GetCMSGame>> getOdds() {
        return ResponseEntity.ok().body(michiganRemainingPrizesService.getRawOdds());
    }

    @GetMapping("/prizes")
    public ResponseEntity<Data> getRemainingPrizes() {
        return ResponseEntity.ok().body(michiganRemainingPrizesService.getRawPrizesRemaining());
    }
}
