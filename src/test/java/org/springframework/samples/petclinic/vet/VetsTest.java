package org.springframework.samples.petclinic.vet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VetsTest {

    @Test
    void testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a Vets object and add some Vet objects to it
        Vets vets = new Vets();
        List<Vet> vetList = new ArrayList<>();
        Vet vet1 = new Vet();
        vet1.setFirstName("John");
        vet1.setLastName("Doe");

        Vet vet2 = new Vet();
        vet2.setFirstName("Jane");
        vet2.setLastName("Smith");

        vetList.add(vet1);
        vetList.add(vet2);
        vets.getVetList().addAll(vetList);

        // Serialize the Vets object to JSON
        String jsonString = objectMapper.writeValueAsString(vets);
        assertNotNull(jsonString);
        System.out.println("Serialized JSON: " + jsonString);
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON string representing a Vets object
        String jsonString = "{\"vetList\":[{\"firstName\":\"John\",\"lastName\":\"Doe\"},{\"firstName\":\"Jane\",\"lastName\":\"Smith\"}]}";

        // Deserialize the JSON string to a Vets object
        Vets vets = objectMapper.readValue(jsonString, Vets.class);
        assertNotNull(vets);
        assertEquals(2, vets.getVetList().size());
        assertEquals("John", vets.getVetList().get(0).getFirstName());
        assertEquals("Doe", vets.getVetList().get(0).getLastName());
        assertEquals("Jane", vets.getVetList().get(1).getFirstName());
        assertEquals("Smith", vets.getVetList().get(1).getLastName());
    }
}