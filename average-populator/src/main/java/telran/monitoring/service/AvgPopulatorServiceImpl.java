package telran.monitoring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.repo.AvgPulseRepo;

@RequiredArgsConstructor
@Slf4j
@Service
public class AvgPopulatorServiceImpl implements AvgPopulatorService {
	final AvgPulseRepo avgPulseRepo;
		
	@Override
	@Transactional(readOnly = false)
	public PulseProbe addProbe(PulseProbe pulseProbe) {
		AvgPulseDoc avgPulseDoc = AvgPulseDoc.of(pulseProbe);
		PulseProbe res = avgPulseRepo.save(avgPulseDoc).build();
		log.trace("AvgPulseValue {} has been added", res);
		return res;
	}

	@Override
	public List<PulseProbe> getAllProbes() {
		List<AvgPulseDoc> resDocs = avgPulseRepo.findAll().stream().toList();
		List<PulseProbe> resProbes = (List<PulseProbe>) resDocs.stream().map(el -> el.build());
		log.trace("Loaded {} avgProbes", resProbes.size());
		return resProbes;
	}

}
