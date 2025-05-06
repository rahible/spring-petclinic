package org.springframework.samples.petclinic.vet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class VetControllerTest {

	@Mock
	private VetRepository vetRepository;

	@InjectMocks
	private VetController vetController;

	@Test
	public void showVetList() {
		final int page = 1;
		final int pageSize = 5;
		final Model model = mock(Model.class);
		final Page<Vet> pageResult = buildPage(page, pageSize);

		when(vetRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

		vetController.showVetList(pageSize, model);
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