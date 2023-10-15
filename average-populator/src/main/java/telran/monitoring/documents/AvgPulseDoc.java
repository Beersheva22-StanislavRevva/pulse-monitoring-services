package telran.monitoring.documents;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import telran.monitoring.dto.PulseProbe;

@Document(collection = "avg-pulse-values")
@Data
@AllArgsConstructor

public class AvgPulseDoc {
	long patientId;
	int value;
	LocalDateTime dateTime;
	
	public static AvgPulseDoc of(PulseProbe pulseProbe) {
		return new AvgPulseDoc(pulseProbe.patientId(), pulseProbe.value(), toLocalDateTime(pulseProbe.timestamp()));
	}
	private static LocalDateTime toLocalDateTime(long timestamp) {
		
		return  LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
	}
	public PulseProbe build() {
		
		return new PulseProbe(patientId, value, dateTime.toEpochSecond(ZoneOffset.UTC), 0);
	}
	
}
