package telran.monitoring.service;

import java.util.List;

import telran.monitoring.dto.PulseProbe;

public interface AvgPopulatorService {
	PulseProbe addProbe(PulseProbe pulseProbe);
	List<PulseProbe> getAllProbes();
	}
