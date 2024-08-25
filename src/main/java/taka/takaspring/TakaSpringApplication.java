package taka.takaspring;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TakaSpringApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		setPropertyIfAbsent("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		setPropertyIfAbsent("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		setPropertyIfAbsent("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		setPropertyIfAbsent("SPRING_MAIL_USERNAME", dotenv.get("SPRING_MAIL_USERNAME"));
		setPropertyIfAbsent("SPRING_MAIL_PASSWORD", dotenv.get("SPRING_MAIL_PASSWORD"));
		setPropertyIfAbsent("SPRING_JWT_SECRET", dotenv.get("SPRING_JWT_SECRET"));

		SpringApplication.run(TakaSpringApplication.class, args);
	}

	private static void setPropertyIfAbsent(String key, String value) {
		if (System.getenv(key) == null && value != null) {
			System.setProperty(key, value);
		}
	}
}
