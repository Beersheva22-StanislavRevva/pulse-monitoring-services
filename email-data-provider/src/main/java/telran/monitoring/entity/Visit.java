package telran.monitoring.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "visits")
@Data
public class Visit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	LocalDate date;
	@ManyToOne
	@JoinColumn(name="doctor_name")
	Doctor doctor;
	@ManyToOne
	@JoinColumn(name="patient_name")
	Patient patient;
	
		
}
