package com.example.demo;

import com.example.demo.base.AbstractTestContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookdeliveryApplicationTests extends AbstractTestContainerConfiguration {

	@Test
	void contextLoads() {
	}

}
