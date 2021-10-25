package edu.eam.ingesoft.ejemploPersons.services

import edu.eam.ingesoft.ejemploPersons.exceptions.BusinessException
import edu.eam.ingesoft.ejemploPersons.models.entities.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PersonServiceTest {

    @Autowired
    lateinit var personService: PersonService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createPersonHappyPathTest() {
        //prerequisitos
        //que la persona no exista, y no una persona con ese nombre.

        //jeceucion de la prueba
        personService.createPerson(Person("1", "juan", 30, "Armenia"))

        //verificaciones..
        val personToAssert = entityManager.find(Person::class.java, "1")
        Assertions.assertNotNull(personToAssert)

        Assertions.assertEquals("juan", personToAssert.name)
    }

    @Test
    fun createPersonPersonAlreadyExistsTest() {
       //prerequisitos
        entityManager.persist(Person("1", "juan", 30, "Armenia"))

        try {
            personService.createPerson(Person("1", "claudia", 31, "Armenia"))
            Assertions.fail()
        } catch (e: BusinessException) {
          Assertions.assertEquals("This person already exists", e.message)
        }
    }

    @Test
    fun createPersonRepeatedNameTest() {
        //prerequisitos
        entityManager.persist(Person("1", "juan", 30, "Armenia"))

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { personService.createPerson(Person("2", "juan", 31, "Armenia")) }
        )

        Assertions.assertEquals("This person with this name already exists", exception.message)
    }
}