package telran.monitoring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.service.PulseValuesBackOfficeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("pulse")
@Slf4j
public class PulseValuesBackOfficeController {
	
	final PulseValuesBackOfficeService service;
	
	@GetMapping("avg/{patientId}/{from}/{to}")
	ResponseEntity<?> getAvg(@PathVariable long patientId, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
		ResponseEntity<?> res = null;
		try {
		int avgValue = service.getAvgValue(patientId, from, to);
		res = new ResponseEntity<Integer>(avgValue, HttpStatus.OK);
		} catch (Exception e) {
		res = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return res;
	}
	
	@GetMapping("max/{patientId}/{from}/{to}")
	ResponseEntity<?> getMax(@PathVariable long patientId, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
		ResponseEntity<?> res = null;
		try {
		int maxValue = service.getMaxValue(patientId, from, to);
		res = new ResponseEntity<Integer>(maxValue, HttpStatus.OK);
		} catch (Exception e) {
		res = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return res;
	}
	
	@GetMapping("all/{patientId}/{from}/{to}")
	ResponseEntity<?> getAll(@PathVariable long patientId, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
		ResponseEntity<?> res = null;
		try {
		List<Integer> allValues = service.getAllValues(patientId, from, to);
		res = new ResponseEntity<List<Integer>>(allValues, HttpStatus.OK);
		} catch (Exception e) {
		res = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return res;
	}
}
