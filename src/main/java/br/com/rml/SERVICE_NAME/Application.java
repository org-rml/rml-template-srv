package br.com.rml.SERVICE_NAME;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

// TODO: Renomear a classe para o nome do serviço. Ex: AuthServiceApplication
@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private final Environment environment;

	public Application(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String activeProfile = Arrays.toString(environment.getActiveProfiles());
		String profiles = defaultIfBlank(activeProfile.replace("[]", ""), "[DEFAULT]");
		LOGGER.info("ACTIVE PROFILES: {}", profiles);
	}
}
