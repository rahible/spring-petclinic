package org.springframework.samples.petclinic.owner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = OwnerController.class)
class OwnerControllerTest {

	@MockitoBean
	private OwnerRepository owners;

	@Autowired
	private OwnerController ownerController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "John")
				.param("lastName", "Doe")
				.param("address", "123 Main St")
				.param("city", "Cityville")
				.param("telephone", "1234567890"))
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}

	@Test
	void testProcessCreationFormHasErrorsWithFlash() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "John").param("lastName", "Doe")) // Missing
																									// required
																									// fields
																									// like
																									// address,
																									// city,
																									// and
																									// telephone
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeHasFieldErrors("owner", "address", "city", "telephone"));

	}

	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testProcessFindFormSuccess() throws Exception {
		Owner owner = new Owner();
		owner.setLastName("Doe");

		Page<Owner> ownersResults = new PageImpl<>(List.of(owner));
		when(owners.findByLastNameStartingWith("Doe", PageRequest.of(0, 5))).thenReturn(ownersResults);

		mockMvc.perform(get("/owners").param("lastName", "Doe"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/" + owner.getId()));
	}

	@Test
	void testInitUpdateOwnerForm() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(Optional.of(owner));

		mockMvc
			.perform(post("/owners/1/edit").param("firstName", "John")
				.param("lastName", "Doe")
				.param("address", "123 Main St")
				.param("city", "Cityville")
				.param("telephone", "1234567890"))
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"));
	}

	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(Optional.of(owner));
		mockMvc.perform(post("/owners/1/edit").param("firstName", "John").param("lastName", "Doe"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeHasFieldErrors("owner", "address", "city", "telephone"));

	}

}