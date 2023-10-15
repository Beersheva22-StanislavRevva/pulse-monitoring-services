package telran.monitoring.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import telran.monitoring.dto.EmailNotificationData;

@Service
@RequiredArgsConstructor
public class EmailDataProviderImpl implements EmailDataProvider {
	final RestTemplate restTemplate;
	@Override
	public EmailNotificationData getData(long patientId) {
		// TODO Auto-generated method stub
		return null;
	}

}
