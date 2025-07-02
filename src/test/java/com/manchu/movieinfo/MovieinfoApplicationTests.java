package com.manchu.movieinfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc

public class MovieinfoApplicationTests {
    @Autowired
	private MockMvc mockMvc;

	//I - Interface: Test the API for 200 and 404 Not Found
	@Test
	void shouldReturn200StatusOnRootEndpoint() throws Exception {
		mockMvc.perform(get("/root"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(status().isOk()); // 200 HTTP Response Code

	}
	@Test
	void shouldReturnNotFoundStatus() throws Exception {
		mockMvc.perform(get("/roots"))
				.andExpect(status().isNotFound()); // 404 HTTP Response Code
	}
	// O - One: Check the basic expected output
    @Test
	void shouldReturnWelcomeMessage() throws Exception {
		mockMvc.perform(get("/root"))
				.andExpect(status().isOk())
				.andExpect(content().string("Welcome to the Spring Boot API Coding!"));

	}

	// B - Boundary: Verify that no extra chars or trailing spaces exist
	@Test
	void welcomeMessageShouldBeExact() throws Exception {
		mockMvc.perform(get("/root"))
				.andExpect(content().string(org.hamcrest.Matchers.equalTo("Welcome to the Spring Boot API Coding!")));
	}
	// E - Exception: Accessing unknown route returns 404
	@Test
	void unknownPathShouldReturn404() throws Exception {
		mockMvc.perform(get("/nonexistent"))
				.andExpect(status().isNotFound());
	}
	@Test
	void applicationStartsSuccessfully() {
		MovieinfoApplication.main(new String[]{});
	}


	@Test
	void contextLoads() {
	}

}
