package br.com.treinaweb.spring_mail;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringMailApplication implements CommandLineRunner {

	private final MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMailApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var to = "cleyson@mail.com";
		var subject = "E-mail de teste";
		var body = "E-mail enviado com o Java Mail Sender e Spring";

		// mailService.sendSimpleMail(to, subject, body);
		mailService.sendTemplatedMail(
			to,
			subject,
			"mail/test-mail.html",
			Map.of("name", "Cleyson"),
			"midia/anexo.txt"
		);
	}

}
