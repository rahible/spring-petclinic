package org.springframework.samples.petclinic.owner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(PetController.class)
public class PetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	private Owner owner;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new PetController(owners)).build();
		owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(Optional.of(owner));
	}

	@Test
	public void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/1/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(
				post("/owners/1/pets/new").param("name", "Buddy").param("birthDate", "2020-01-01").param("type", "cat"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new").param("name", "") // Name is empty
			.param("birthDate", "2020-01-01")
			.param("type", "cat"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));

	}

	@Test
	public void testInitUpdateForm() throws Exception {
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		when(owners.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/pets/1/edit"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessUpdateFormSuccess() throws Exception {
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		when(owners.findById(1)).thenReturn(Optional.of(owner));

		mockMvc
			.perform(post("/owners/1/pets/1/edit").param("name", "Buddy")
				.param("birthDate", "2020-01-01")
				.param("type", "cat"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"));
	}

	@Test
	public void testProcessUpdateFormHasErrors() throws Exception {
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		when(owners.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(post("/owners/1/pets/1/edit").param("name", "") // Name is empty
			.param("birthDate", "2020-01-01")
			.param("type", "cat"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

}