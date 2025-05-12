package org.springframework.samples.petclinic.vet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VetController.class)
class VetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VetRepository vetRepository;

	private List<Vet> vets;

	private Page<Vet> vetPage;

	@BeforeEach
	void setup() {
		Vet vet = new Vet();
		vet.setFirstName("John");
		vet.setLastName("Doe");
		vets = Collections.singletonList(vet);
		vetPage = new PageImpl<>(vets, PageRequest.of(0, 5), 1);
	}

	@Test
	void testShowVetList() throws Exception {
		when(vetRepository.findAll(Mockito.any(Pageable.class))).thenReturn(vetPage);

		mockMvc.perform(get("/vets.html"))
			.andExpect(status().isOk())
			.andExpect(view().name("vets/vetList"))
			.andExpect(model().attributeExists("currentPage"))
			.andExpect(model().attributeExists("totalPages"))
			.andExpect(model().attributeExists("totalItems"))
			.andExpect(model().attributeExists("listVets"))
			.andExpect(model().attribute("listVets", vets));
	}

	@Test
	void testShowResourcesVetList() throws Exception {
		when(vetRepository.findAll()).thenReturn(vets);

		mockMvc.perform(get("/vets"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vetList[0].firstName").value("John"))
			.andExpect(jsonPath("$.vetList[0].lastName").value("Doe"));
	}

}
