package org.springframework.samples.petclinic.vet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VetController.class)
@DisabledInNativeImage
@DisabledInAotMode
class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VetRepository vetRepository;

	@Test
	void showResourcesVetList() throws Exception {
		final int page = 1;
		final int pageSize = 5;
		final Page<Vet> pageResult = buildPage(page, pageSize);
		when(vetRepository.findAll(any(Pageable.class))).thenReturn(pageResult);
		mockMvc.perform(get("/vets.html"))
			.andExpect(status().isOk())
			.andExpect(view().name("vets/vetList"))
			.andExpect(model().attribute("currentPage", 1))
			.andExpect(model().attribute("totalPages", 2))
			.andExpect(model().attribute("totalItems", 6L))
			.andExpect(model().attributeExists("listVets"));
	}

	private Page<Vet> buildPage(final int pageNumber, final int pageSize) {
		final List<Vet> vets = buildVets();
		final Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return new PageImpl<Vet>(vets, pageable, pageSize);

	}

	private List<Vet> buildVets() {
		final Vet vet = new Vet();
		vet.setFirstName("Steve");
		vet.setLastName("Harris");
		return Collections.singletonList(vet);
	}

}