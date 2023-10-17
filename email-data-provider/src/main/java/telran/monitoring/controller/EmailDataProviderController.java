package telran.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.DoctorDto;
import telran.monitoring.repo.DoctorRepo;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;
import telran.monitoring.service.EmailDataProviderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("doctor")
@Slf4j


public class EmailDataProviderController {
	
	final EmailDataProviderService emailDataProviderService;
	
	@GetMapping("data/{patientId}")
	ResponseEntity<?> getData(@PathVariable long patientId) {
		try {
		String doctorMail = emailDataProviderService.findDoctorMailByPatientId(patientId);
		return ResponseEntity.ok(doctorMail);
		} catch (Exception e) {
		log.error("couldn't find doctor by patient id - {}", e.getMessage());
		return 	ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
