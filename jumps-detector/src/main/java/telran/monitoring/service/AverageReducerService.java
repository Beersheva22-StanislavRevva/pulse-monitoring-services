package telran.monitoring.service;

import telran.monitoring.dto.AveragePulse;
import telran.monitoring.dto.PulseProbe;

public interface AverageReducerService {
	AveragePulse processPulseProbe(PulseProbe pulse);
}
