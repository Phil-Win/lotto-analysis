package phil.win.lottoanalysis.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import phil.win.lottoanalysis.model.raw.prizesremain.GetRetailTopPrizesRemainingByGameType;
import phil.win.lottoanalysis.model.transformed.basic.Prize;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class TransformRawDataUtilTest {
    private ObjectMapper    mapper  =   new ObjectMapper();
    private File file               =   new File("src/test/resources/RemainingPrizesExample.json");

    @Test
    void getRemainingPrizeCount_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        log.debug("Printing the remaining prizes: {}", inputGetRetailTopPrizesRemainingByGameType.toString());
        Long expectedCount  = Long.valueOf(2798777);

        assertEquals(expectedCount, TransformRawDataUtil.getRemainingPrizeCount(inputGetRetailTopPrizesRemainingByGameType));
    }

    @Test
    void getRemainingTicketCount_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        String inputOdds    =   "1 in 4.64";
        Long expectedOutput =   Long.valueOf(15785102);
        assertEquals(expectedOutput, TransformRawDataUtil.getRemainingTicketCount(inputGetRetailTopPrizesRemainingByGameType, inputOdds));
    }

    @Test
    void getInitialPrizeCount_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        log.debug("Printing the remaining prizes: {}", inputGetRetailTopPrizesRemainingByGameType.toString());
        Long expectedCount  = Long.valueOf(3237303);

        assertEquals(expectedCount, TransformRawDataUtil.getInitialPrizeCount(inputGetRetailTopPrizesRemainingByGameType));
    }

    @Test
    void getInitialTicketCount_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        String inputOdds    =   "1 in 4.64";
        Long expectedOutput =   Long.valueOf(18258389);
        assertEquals(expectedOutput, TransformRawDataUtil.getInitialTicketCount(inputGetRetailTopPrizesRemainingByGameType, inputOdds));
    }

    @Test
    void getListOfRemainingPrizes_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        String inputOdds    =   "1 in 4.64";
        List<Prize> expectedOutput  =   new ArrayList<Prize>();
        expectedOutput.add(new Prize(Long.valueOf(1),Long.valueOf(1305111)));
        expectedOutput.add(new Prize(Long.valueOf(2),Long.valueOf(864356)));
        expectedOutput.add(new Prize(Long.valueOf(5),Long.valueOf(428488)));
        expectedOutput.add(new Prize(Long.valueOf(10),Long.valueOf(170737)));
        expectedOutput.add(new Prize(Long.valueOf(20),Long.valueOf(25432)));
        expectedOutput.add(new Prize(Long.valueOf(50),Long.valueOf(3395)));
        expectedOutput.add(new Prize(Long.valueOf(100),Long.valueOf(843)));
        expectedOutput.add(new Prize(Long.valueOf(500),Long.valueOf(415)));
        expectedOutput.add(new Prize(Long.valueOf(0),Long.valueOf(12986325)));

        assertEquals(expectedOutput, TransformRawDataUtil.getListOfRemainingPrizes(inputGetRetailTopPrizesRemainingByGameType, inputOdds));
    }

    @Test
    void getListOfInitialPrizes_InitialExample() throws IOException {
        GetRetailTopPrizesRemainingByGameType inputGetRetailTopPrizesRemainingByGameType   =   mapper.readValue(file, GetRetailTopPrizesRemainingByGameType.class);
        String inputOdds    =   "1 in 4.64";
        List<Prize> expectedOutput  =   new ArrayList<Prize>();
        expectedOutput.add(new Prize(Long.valueOf(1),Long.valueOf(1500718)));
        expectedOutput.add(new Prize(Long.valueOf(2),Long.valueOf(1000589)));
        expectedOutput.add(new Prize(Long.valueOf(5),Long.valueOf(500312)));
        expectedOutput.add(new Prize(Long.valueOf(10),Long.valueOf(200184)));
        expectedOutput.add(new Prize(Long.valueOf(20),Long.valueOf(29976)));
        expectedOutput.add(new Prize(Long.valueOf(50),Long.valueOf(4022)));
        expectedOutput.add(new Prize(Long.valueOf(100),Long.valueOf(1002)));
        expectedOutput.add(new Prize(Long.valueOf(500),Long.valueOf(500)));
        expectedOutput.add(new Prize(Long.valueOf(0),Long.valueOf(15021086)));

        assertEquals(expectedOutput, TransformRawDataUtil.getListOfInitialPrizes(inputGetRetailTopPrizesRemainingByGameType, inputOdds));
    }

    @Test
    void transformTicketPriceStringToDecimal_InitialExample() {
        BigDecimal  expectedValue   =   new BigDecimal("5.465416");
        String      inputValue      =   "$5.465416";
        assertEquals(expectedValue, TransformRawDataUtil.transformTicketPriceStringToBigDecimal(inputValue));
    }
}