package telran.monitoring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.*;
import telran.monitoring.entity.LastPulseValue;
import telran.monitoring.repo.LastPulseValueRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AverageReducerServiceImpl implements AverageReducerService {

	final LastPulseValueRepo valuesRepo;
	@Value("${app.jumps.counts:10}")
	int jumpCounts;
	
	@Override
	public AveragePulse processPulseProbe(PulseProbe pulse) {
		AveragePulse res = null;
		if (valuesRepo.count() == jumpCounts) {
		List<LastPulseValue> tmp = new ArrayList<LastPulseValue>();
		valuesRepo.findAll().forEach(el -> tmp.add(el));
			
		}
		
		return null;
	}

}
