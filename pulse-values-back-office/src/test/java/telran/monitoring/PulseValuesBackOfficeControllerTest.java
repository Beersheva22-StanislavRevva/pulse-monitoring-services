package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import telran.monitoring.controller.PulseValuesBackOfficeController;
import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.service.PulseValuesBackOfficeService;

@RequiredArgsConstructor
@WebMvcTest(value = {PulseValuesBackOfficeController.class})
class PulseValuesBackOfficeControllerTest {
	
	final LocalDateTime DATE1 = LocalDateTime.parse("2023-10-01T00:00:00");
	final LocalDateTime DATE2 = LocalDateTime.parse("2023-10-02T00:00:00");
	final int VALUE1 = 100;
	final int VALUE2 = 120;
	final int VALUE3 = 140;
	
	static final long PATIENT_ID_NORMAL=123l;
	List<Integer> expected = Arrays.asList(VALUE1, VALUE2, VALUE3);
	List<Integer> expectedEmpty = new ArrayList<Integer>();
    static final String ERROR_RESPONSE = "patient not found";
	private static final long PATIENT_ID_NOT_EXIST = 125;
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	PulseValuesBackOfficeService service;
	
	
	@BeforeEach
	void serviceMocking() {
		when(service.getAllValues(PATIENT_ID_NORMAL, DATE1, DATE2)).thenReturn(expected);
		when(service.getAllValues(PATIENT_ID_NOT_EXIST, DATE1, DATE2)).thenReturn(expectedEmpty);
	}
	
	long toEpochMilli(LocalDateTime date) {
		Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();	
		return instant.toEpochMilli();
	}

	@Test
	void normalFlow() throws Exception {
		String responseJSON = mockMvc.perform(get("http://localhost:8080/pulse/all/" + PATIENT_ID_NORMAL + "/" + DATE1.toString() + "/" + DATE2.toString())).andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		assertEquals(expected, mapper.readValue(responseJSON, List.class));
	}
	
	@Test
	void abnormalFlow() throws Exception {
		String responseJSON = mockMvc.perform(get("http://localhost:8080/pulse/all/" + PATIENT_ID_NOT_EXIST + "/" + DATE1.toString() + "/" + DATE2.toString())).andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		assertEquals(expectedEmpty, mapper.readValue(responseJSON, List.class));
	}


}
