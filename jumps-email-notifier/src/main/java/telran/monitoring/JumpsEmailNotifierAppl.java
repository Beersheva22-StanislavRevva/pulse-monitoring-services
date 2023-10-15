package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.dto.JumpPulse;
import telran.monitoring.service.EmailDataProvider;

@SpringBootApplication	
@RequiredArgsConstructor
@Slf4j
public class JumpsEmailNotifierAppl {
	final JavaMailSender mailSender;
	final EmailDataProvider dataProvider;
	@Value("${app.email.service.address:hospitalservice@hospital.com}")
	private String hospitalServiceMail;
	@Value("${app.email.service.name:Hospital Alert Service}")
	private String hospitalServiceName;
	@Value("${app.email.subject:Pulse Jump }")
	private String subject;
	public static void main(String[] args) {
		SpringApplication.run(JumpsEmailNotifierAppl.class, args);

	}
	Consumer<JumpPulse> jumpsConsumer() {
		return this::jumpProcessing;		
	}
	void jumpProcessing(JumpPulse jumpPluse) {
		log.trace("recieved: {}", jumpPluse);
		sendMail(jumpPluse);
	}
	private void sendMail(JumpPulse jumpPluse) {
		EmailNotificationData data = dataProvider.getData(jumpPluse.patientId());
		if (data == null) {
			log.warn("email data have not been recieved");
			data = new EmailNotificationData(hospitalServiceMail, hospitalServiceName,
			"" + jumpPluse.patientId());
		}
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(data.doctorMail());
		smm.setSubject(subject + data.patientName());
		String text = getText(jumpPluse, data); 
		smm.setText(text);
		mailSender.send(smm);
		log.trace("sent: {}", text);
	}
	private String getText(JumpPulse jumpPluse, EmailNotificationData data) {
		
		return String.format("Dear % s\nYour patient %s has the pulse jump\n"
		+ "previous value: %d\n"
		+ "current value: %d\n", data.doctorName(), data.patientName(),
			jumpPluse.prevValue(), jumpPluse.currentValue());
	}

}
