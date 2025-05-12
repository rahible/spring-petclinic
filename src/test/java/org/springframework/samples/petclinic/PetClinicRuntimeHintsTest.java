package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.ResourceHints;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.SerializationHints;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.vet.Vet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PetClinicRuntimeHintsTest {

	@Test
	void registerHints() {
		PetClinicRuntimeHints petClinicRuntimeHints = new PetClinicRuntimeHints();
		RuntimeHints runtimeHints = mock(RuntimeHints.class);
		ResourceHints resourceHints = mock(ResourceHints.class);
		SerializationHints serializationHints = mock(SerializationHints.class);
		when(runtimeHints.resources()).thenReturn(resourceHints);
		when(runtimeHints.serialization()).thenReturn(serializationHints);

		petClinicRuntimeHints.registerHints(runtimeHints, getClass().getClassLoader());

		// Verify resource patterns
		verify(resourceHints, times(1)).registerPattern("db/*");
		verify(resourceHints, times(1)).registerPattern("messages/*");
		verify(resourceHints, times(1)).registerPattern("mysql-default-conf");

		// Verify serialization types
		verify(serializationHints).registerType(BaseEntity.class);
		verify(serializationHints).registerType(Person.class);
		verify(serializationHints).registerType(Vet.class);
	}

}