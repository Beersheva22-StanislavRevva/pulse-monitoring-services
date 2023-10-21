package telran.monitoring.batch;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.entities.Doctor;
import telran.monitoring.entities.Patient;
import telran.monitoring.entities.Visit;
import telran.monitoring.repo.DoctorRepo;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;

@Component
@RequiredArgsConstructor
@Slf4j
public class RandomDBCreation {
	final DoctorRepo doctorRepo;
	final PatientRepo patientRepo;
	final VisitRepo visitRepo;
	
	@Value("${app.random.doctors.amount:10}")
    int nDoctors;
	@Value("${app.random.patients.amount:100}")
    int nPatients;
	@Value("${app.random.visits.amount:100}")
    int nVisits;
	 @Value("${app.random.creation.enable:false}")
     boolean creationEnable;
	 
	 @PostConstruct
	 void createDb() {
		 if(creationEnable) {
			 List<Doctor> doctorList = 
					 IntStream.rangeClosed(1, nDoctors).mapToObj(this::getDoctor).toList();
			 doctorRepo.saveAll(doctorList);
			 List<Patient> patientList = 
					 IntStream.rangeClosed(1000, 1000 + nPatients).mapToObj(this::getPatient).toList();
			 patientRepo.saveAll(patientList); 
			 List<Visit> visitList = 
					 IntStream.rangeClosed(1, nVisits).mapToObj(this::getVisit).toList();
			 visitRepo.saveAll(visitList);
			 
		 }
	 }
	Doctor getDoctor(int id) {
		 String name = "doctor" + id;
		String email = name + "@hospital.com";
		return new Doctor(id, email , name);
	 }
	Patient getPatient(int id) {
		 String name = "patient" + id;
		String email = name + "@mailprovider.com";
		return new Patient(id, email , name);
	 }
	Visit getVisit(int id) {
		LocalDate date = getRandomDate();
		Doctor randomDoctor = doctorRepo.findById((long)getRandomNumber(1,(int)doctorRepo.count())).get();
		Patient randomPatient = patientRepo.findById((long)getRandomNumber(1000, 1000 + (int)patientRepo.count())).get();
		Visit res = new Visit(randomDoctor, randomPatient, date);
		return res;
	 }
	private LocalDate getRandomDate() {
		
		int year = getRandomNumber(2023, 2024);
		int month = getRandomNumber(1,13);
		int day = getRandomNumber(1, 29);
		return LocalDate.of(year , month , day );
	}
	private int getRandomNumber(int min, int max) {
		
		return ThreadLocalRandom.current().nextInt(min, max);
	}
}
