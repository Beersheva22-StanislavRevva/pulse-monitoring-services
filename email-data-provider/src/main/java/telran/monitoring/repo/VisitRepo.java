package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.monitoring.entity.Visit;

public interface VisitRepo extends JpaRepository<Visit, Integer> {

	@Query(value = "select doctor_name from visits where patient_name =:patientName order by date desc limit 1", nativeQuery = true)
	String getDoctorName(String patientName);

}
