package edu.uclm.esi.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import edu.uclm.esi.games.web.Manager;
import edu.uclm.esi.games.web.WebController;
import edu.uclm.esi.games.ws.WSConfiguration;
import edu.uclm.esi.games.ws.WSServer;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan( basePackageClasses = {WebController.class, Manager.class, WSServer.class, WSConfiguration.class} )
public class Lanzadora {

	public static void main(String[] args) {
		SpringApplication.run(Lanzadora.class, args);
	}

}
