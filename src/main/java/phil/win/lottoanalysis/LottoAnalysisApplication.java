package phil.win.lottoanalysis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LottoAnalysisApplication {

	public static void main(String[] args)  {
        SpringApplication.run(LottoAnalysisApplication.class, args);
//		InstantGamePrizesRemainingResponse  instantGamePrizesRemainingResponse;
//		RequestPayload	requestPayload;
//		RestTemplate restTemplate    =   new RestTemplate();
//		ObjectMapper	mapper	=	new ObjectMapper();
//		requestPayload	=	mapper.readValue(new File("src/main/resources/RequestPayloadRemainingPrizes.json"), RequestPayload.class);
//		instantGamePrizesRemainingResponse  =   restTemplate.postForObject(new URI(REQUEST_URL_AS_STRING), requestPayload, InstantGamePrizesRemainingResponse.class);
//		try {
//			log.info("Printing the prizes remaining: {} ", mapper.writeValueAsString(instantGamePrizesRemainingResponse));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}
}
