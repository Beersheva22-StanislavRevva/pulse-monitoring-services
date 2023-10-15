package telran.monitoring;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.service.AvgPopulatorService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AveragePopulatorAppl {
@Autowired
AvgPopulatorService avgPopulatorService;

	public static void main(String[] args) {
		SpringApplication.run(AveragePopulatorAppl.class, args);

	}
	@Bean
	Consumer<PulseProbe> pulseProbeConsumer() {
		return this::probeConsumer;
	}
	void probeConsumer(PulseProbe pulseProbe) {
		log.trace("received {}", pulseProbe);
		try {
			avgPopulatorService.addProbe(pulseProbe);
		} catch (Exception e) {
		log.error("pulseProbe wasn't added to db - " + e.getMessage());
			
		}
	}

}
