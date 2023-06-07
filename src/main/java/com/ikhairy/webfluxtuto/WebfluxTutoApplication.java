package com.ikhairy.webfluxtuto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class WebfluxTutoApplication {

	public static void main(String[] args) {
		//ReactorDebugAgent.init();
		SpringApplication.run(WebfluxTutoApplication.class, args);
	}

}
