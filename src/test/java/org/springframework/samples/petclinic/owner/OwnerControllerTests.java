package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OwnerControllerTests {

	@Mock
	private OwnerRepository owners;

	@InjectMocks
	private OwnerController controller;

	@Test
	void initCreationForm() {
		final String form = this.controller.initCreationForm();
		assertEquals("owners/createOrUpdateOwnerForm", form);
	}

}
