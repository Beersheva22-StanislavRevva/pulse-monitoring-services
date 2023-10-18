package telran.monitoring.service;

import java.time.LocalDateTime;
import java.util.List;

public interface PulseValuesBackOfficeService {
	
int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to); //in the case no values exist in a given range, 0 should be returned
int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to); //in the case no values exist in a given range, 0 should be returned
List<Integer> getAllValues(long patientId, LocalDateTime from, LocalDateTime to); //in the case no values exist, empty list should be returned

}
