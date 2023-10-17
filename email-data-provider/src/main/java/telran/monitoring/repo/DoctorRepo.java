package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.monitoring.entity.Doctor;

public interface DoctorRepo extends JpaRepository<Doctor, String> {
	
	@Query(value = "select doctor_mail from doctors where doctor_name =:doctorName", nativeQuery = true)
	String getDoctorMail(String doctorName);
	
}
