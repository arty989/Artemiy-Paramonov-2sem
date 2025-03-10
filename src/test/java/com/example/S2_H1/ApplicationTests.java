package com.example.S2_H1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("no-db-test")
class ApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
