package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

class PetValidatorTest {

	private PetValidator petValidator;

	@BeforeEach
	void setUp() {
		petValidator = new PetValidator();
	}

	@Test
	void supports() {
		assertTrue(petValidator.supports(Pet.class));
		assertFalse(petValidator.supports(Object.class));
	}

	@Test
	void validateShouldPassForNewPet() {
		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("CAT");
		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(cat);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertFalse(errors.hasErrors());
	}

	@Test
	void validateShouldPassForNotNewPet() {
		PetType cat = new PetType();
		cat.setId(1);
		cat.setName("CAT");
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(cat);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertFalse(errors.hasErrors());
	}

	@Test
	void validateShouldFailWhenNameIsEmpty() {
		Pet pet = new Pet();
		pet.setName("");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertTrue(errors.hasErrors());
		assertTrue(errors.hasFieldErrors("name"));
	}

	@Test
	void validateShouldFailWhenBirthDateIsNull() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setBirthDate(null);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertTrue(errors.hasErrors());
		assertTrue(errors.hasFieldErrors("birthDate"));
	}

	@Test
	void validateShouldFailWhenNameAndBirthDateAreInvalid() {
		Pet pet = new Pet();
		pet.setName("");
		pet.setBirthDate(null);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertTrue(errors.hasErrors());
		assertTrue(errors.hasFieldErrors("name"));
		assertTrue(errors.hasFieldErrors("birthDate"));
	}

	@Test
	void validateShouldFailWhenTypeIsInvalid() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		Errors errors = new BeanPropertyBindingResult(pet, "pet");
		petValidator.validate(pet, errors);

		assertTrue(errors.hasErrors());
		assertTrue(errors.hasFieldErrors("type"));
	}

}