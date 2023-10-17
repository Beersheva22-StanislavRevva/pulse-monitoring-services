package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.monitoring.service.EmailDataProviderService;


@SpringBootTest

class ProviderReadTest {
	@Autowired
	EmailDataProviderService emailDataProviderService;
	@Sql(scripts = {"provider-read-test-script.sql"})
	@Test
	void SimpeReadTest() {
		String exampleMail = "izhak@hospital.com";
		String recievedMail = emailDataProviderService.findDoctorMailByPatientId(124l);
		assertEquals(exampleMail, recievedMail);
	}

}
