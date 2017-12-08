package rocks.lipe.hotmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class HotmessageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HotmessageApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HotmessageApplication.class);
	}
}
