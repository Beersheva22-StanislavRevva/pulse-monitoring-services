package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.monitoring.dto.PatientDto;
import telran.monitoring.entity.Patient;

public interface PatientRepo extends JpaRepository<Patient, Long> {
	@Query(value = "select patient_name from patients where patient_id =:patientId", nativeQuery = true)
	String getPatientName(long patientId);
	
}
