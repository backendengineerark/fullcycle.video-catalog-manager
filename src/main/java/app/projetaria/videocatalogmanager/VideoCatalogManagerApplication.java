package app.projetaria.videocatalogmanager;

import app.projetaria.videocatalogmanager.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VideoCatalogManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServerConfig.class, args);
	}

}
