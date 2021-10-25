package edu.eam.ingesoft.ejemploPersons.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.eam.ingesoft.ejemploPersons.exceptions.ErrorResponse
import edu.eam.ingesoft.ejemploPersons.models.entities.Contact
import edu.eam.ingesoft.ejemploPersons.models.entities.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
//arrancar el servidor web
@AutoConfigureMockMvc
class PersonControllerTest {

    //clase que simula el servidor web que expone las operaciones de los controlladores
    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun findPersonByIdHappyPathTest() {
        //prerequisitos..
        entityManager.persist(Person("1", "juan", 30, "Armenia"))

        //creando la peticion...
        val request = MockMvcRequestBuilders.get("/persons/1")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  person = objectMapper.readValue(json, Person::class.java)
        Assertions.assertEquals("juan", person.name)
        Assertions.assertEquals(30, person.age)
        Assertions.assertEquals("Armenia", person.city)
        //Assertions.assertEquals("{\"id\":\"1\",\"name\":\"juan\",\"age\":30,\"city\":\"Armenia\"}", body)
    }

    @Test
    fun findPersonByIdNotFoundTest() {
        //creando la peticion...
        val request = MockMvcRequestBuilders.get("/persons/1")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isNotFound)

        val json = response.andReturn().response.contentAsString;
        Assertions.assertEquals("{\"message\":\"Person not found\",\"code\":404}", json)

        val error = objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("Person not found", error.message)
        Assertions.assertEquals(404, error.code)
    }

    @Test
    fun createContactHappyPathTest() {
        //prerequisitos..
        entityManager.persist(Person("1", "juan", 30, "Armenia"))

        val body = """
           {
            "id": "35345",
            "name": "juan ferrer",
            "phone": "64546123451",
            "address": "muy lejos de aca"
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/persons/1/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createContactPersonNotFoundTest() {
        val body = """
           {
            "id": "35345",
            "name": "juan ferrer",
            "phone": "64546123451",
            "address": "muy lejos de aca"
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/persons/1/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"The person does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createContactMoreThan3ContactsFoundTest() {
        val person = Person("1", "gladys", 10, "Armenia")
        entityManager.persist(person)
        entityManager.persist(
            Contact(
                "1",
                "appa",
                "1231231234",
                "la patagonia", "correo@correo.com", person)
        )

        entityManager.persist(
            Contact(
                "2",
                "juana",
                "1231231235",
                "la patagonia", "correo@correo.com", person)
        )

        entityManager.persist(
            Contact(
                "3",
                "mauricia",
                "1231231235",
                "la patagonia", "correo@correo.com", person)
        )
        val body = """
           {
            "id": "35345",
            "name": "juan ferrer",
            "phone": "6454612345",
            "address": "muy lejos de aca"
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/persons/1/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"Only 3 contacts by person\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createContactRepeatedTest() {
        val person = Person("1", "gladys", 10, "Armenia")
        entityManager.persist(person)
        entityManager.persist(
            Contact(
                "1",
                "appa",
                "1231231234",
                "la patagonia", "correo@correo.com", person)
        )

        val body = """
           {
            "id": "35345",
            "name": "appa",
            "phone": "1231231234",
            "address": "muy lejos de aca"
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/persons/1/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"Repeated contact\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }



}