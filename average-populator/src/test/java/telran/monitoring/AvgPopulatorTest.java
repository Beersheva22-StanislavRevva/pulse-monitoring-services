package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.repo.AvgPulseRepo;
import telran.monitoring.service.AvgPopulatorService;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AvgPopulatorTest {
	static final long PATIENT_ID_NO_REDIS = 123L;
	static final long PATIENT_ID_NO_AVG = 124l;
	static final long PATIENT_ID_AVG = 125L;
	static final int VALUE1 = 100;
	static final int VALUE2 = 120;
	@Autowired
	InputDestination producer;
	@Autowired
	AvgPopulatorService avgPopulatorService;
	@Autowired
	AvgPulseRepo avgPulseRepo;
	PulseProbe probeNoRedis = new PulseProbe(PATIENT_ID_NO_REDIS, VALUE1, 0, 0);
	PulseProbe probeNoAvg = new PulseProbe(PATIENT_ID_NO_AVG, VALUE1, 0, 0);
	PulseProbe probeAvg = new PulseProbe(PATIENT_ID_AVG, VALUE2, 0, 0);
	String consumerBindingName = "pulseProbeConsumer-in-0";
	
	
	@Test
	void AddProbeTest() {
		producer.send(new GenericMessage<PulseProbe>(probeAvg), consumerBindingName);
		avgPopulatorService.addProbe(probeAvg);
		AvgPulseDoc avgPulseDoc = AvgPulseDoc.of(probeAvg);
		avgPulseRepo.save(avgPulseDoc);
		
	}

}
