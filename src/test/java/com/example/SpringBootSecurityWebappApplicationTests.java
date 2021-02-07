package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class SpringBootSecurityWebappApplicationTests {

	@Value("${spring.application.name}")
	private String springApplicationName;

	@Test
	void contextLoads() {
		System.out.printf("Currently running %s%n", springApplicationName);
	}
}
