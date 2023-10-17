package telran.monitoring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.repo.DoctorRepo;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmailDataProviderServiceImpl implements EmailDataProviderService {
	
	final DoctorRepo doctorRepo;
	final PatientRepo patientRepo;
	final VisitRepo visitRepo;
	
	@Override
	public String findDoctorMailByPatientId(long patientId) {
		String patientName = patientRepo.getPatientName(patientId);
		String doctorName = visitRepo.getDoctorName(patientName);
		String doctorMail = doctorRepo.getDoctorMail(doctorName);
		log.trace("patient name: {}, doctor name: {}, doctor mail:", patientName, doctorName, doctorMail);
		return doctorMail;
	}

}
