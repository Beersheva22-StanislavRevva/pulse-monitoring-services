package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.repo.AvgPulseRepo;
import telran.monitoring.service.PulseValuesBackOfficeService;

@SpringBootTest
class PulseValuesBackOfficeServiceTest {
	@Autowired
	AvgPulseRepo avgPulseRepo;
	@Autowired
	PulseValuesBackOfficeService service;
	
	final long PATIENT_ID1 = 123l;
	final long PATIENT_ID2 = 124l;
	final long PATIENT_ID3 = 125l;
	final LocalDateTime DATE1 = LocalDateTime.parse("2023-10-01T00:00:00");
	final LocalDateTime DATE2 = LocalDateTime.parse("2023-10-02T00:00:00");
	final LocalDateTime DATE3 = LocalDateTime.parse("2023-10-03T00:00:00");
	final LocalDateTime WRONG_DATE1 = LocalDateTime.parse("2023-09-01T00:00:00");
	final LocalDateTime WRONG_DATE2 = LocalDateTime.parse("2023-09-30T00:00:00");
	final int VALUE1 = 100;
	final int VALUE2 = 120;
	final int VALUE3 = 140;
	
	PulseProbe pulseProbe1 = new PulseProbe(PATIENT_ID1, VALUE1, toEpochMilli(DATE1), 0);
	PulseProbe pulseProbe2 = new PulseProbe(PATIENT_ID1, VALUE2, toEpochMilli(DATE2), 0);
	PulseProbe pulseProbe3 = new PulseProbe(PATIENT_ID1, VALUE3, toEpochMilli(DATE3), 0);
	AvgPulseDoc doc1 = AvgPulseDoc.of(pulseProbe1);
	AvgPulseDoc doc2 = AvgPulseDoc.of(pulseProbe2);
	AvgPulseDoc doc3 = AvgPulseDoc.of(pulseProbe3);

	@BeforeEach
	void setUp() {
	avgPulseRepo.deleteAll();
	avgPulseRepo.saveAll(Arrays.asList(doc1, doc2, doc3));
	}
	
	long toEpochMilli(LocalDateTime date) {
		Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();	
		return instant.toEpochMilli();
	}
	
	@Test
	void getAllValuesTest() {
		List<Integer> resList = service.getAllValues(PATIENT_ID1, DATE1, DATE3);		
		List<Integer> expected = Arrays.asList(doc1.getValue(), doc2.getValue(), doc3.getValue());
		assertIterableEquals(expected, resList);
		resList = service.getAllValues(PATIENT_ID1, WRONG_DATE1, WRONG_DATE2);
		assertIterableEquals(new ArrayList<Integer>(), resList);
		resList = service.getAllValues(PATIENT_ID2, DATE1, DATE3);
		assertIterableEquals(new ArrayList<Integer>(), resList);
		
	}
	
	@Test
	void getAvgValueTest() {
		int res = service.getAvgValue(PATIENT_ID1, DATE1, DATE3);
		int expected = (VALUE1 + VALUE2 + VALUE3) / 3; 
		assertEquals(expected, res);
		res = service.getAvgValue(PATIENT_ID1, WRONG_DATE1, WRONG_DATE2);
		assertEquals(0, res);
		res = service.getAvgValue(PATIENT_ID2, DATE1, DATE3);
		assertEquals(0, res);
	}
	
	@Test
	void getMaxValueTest() {
		int res = service.getMaxValue(PATIENT_ID1, DATE1, DATE3);
		int expected = VALUE3; 
		assertEquals(expected, res);
		res = service.getMaxValue(PATIENT_ID1, WRONG_DATE1, WRONG_DATE2);
		assertEquals(0, res);
	}

}
