package telran.monitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@Data
public class Patient {
  @Column(name="patient_id")
  long patientId;
  @Id
  @Column(name="patient_name")
  String patientName;
  
}
