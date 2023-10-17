package telran.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@Data
public class Doctor {
	@Column(name="doctor_mail")
	String doctorMail;
	@Id
	@Column(name="doctor_name")
	String doctorName;
    
	  
}
