package org.springframework.samples.petclinic.owner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository controller;

	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void processCreationForm() throws Exception {
		mockMvc.perform((post("/owners/new")
				.param("address", "1102 Grant St")
				.param("city", "Danville")
				.param("lastName", "Harris")
				.param("firstName", "Steve")
				.param("telephone", "2174422231")))
		.andExpect(status().is3xxRedirection());
		
	}

	@Test
	void processCreationForm_invalidPhone() throws Exception {
		mockMvc.perform((post("/owners/new")
				.param("address", "1102 Grant St")
				.param("city", "Danville")
				.param("lastName", "Harris")
				.param("firstName", "Steve")
				.param("telephone", "12174422231")))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		
	}
}
