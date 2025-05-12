package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetTypeFormatterTest {

    private PetTypeFormatter petTypeFormatter;
    
    @Mock
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void setup() {
        petTypeFormatter = new PetTypeFormatter(ownerRepository);
    }

    @Test
    public void testPrint() {
        PetType petType = new PetType();
        petType.setName("Dog");
        String petTypeName = petTypeFormatter.print(petType, Locale.ENGLISH);
        assertEquals("Dog", petTypeName);
    }

    @Test
    public void testParseValid() throws ParseException {
        PetType petType1 = new PetType();
        petType1.setName("Dog");
        PetType petType2 = new PetType();
        petType2.setName("Cat");
        List<PetType> petTypes = new ArrayList<>();
        petTypes.add(petType1);
        petTypes.add(petType2);

        Mockito.when(ownerRepository.findPetTypes()).thenReturn(petTypes);

        PetType parsedPetType = petTypeFormatter.parse("Dog", Locale.ENGLISH);
        assertEquals("Dog", parsedPetType.getName());
    }

    @Test
    public void testParseInvalid() {
        PetType petType1 = new PetType();
        petType1.setName("Dog");
        PetType petType2 = new PetType();
        petType2.setName("Cat");
        List<PetType> petTypes = new ArrayList<>();
        petTypes.add(petType1);
        petTypes.add(petType2);

        Mockito.when(ownerRepository.findPetTypes()).thenReturn(petTypes);

        assertThrows(ParseException.class, () -> {
            petTypeFormatter.parse("Bird", Locale.ENGLISH);
        });
    }
}