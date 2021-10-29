package edu.eam.ingesoft.ejemploPersons.services

import edu.eam.ingesoft.ejemploPersons.exceptions.BusinessException
import edu.eam.ingesoft.ejemploPersons.models.entities.Contact
import edu.eam.ingesoft.ejemploPersons.models.entities.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ContactServiceTest {

    @Autowired
    lateinit var contactService: ContactService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun addContactToPersonPersonNotFoundTest() {
        val contact = Contact("1", "gladys", "123123",
            "no se", "correo@correo.com",null)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                contactService.addContactToPerson(contact,"1")
            }
        )

        Assertions.assertEquals("The person does not exist", exc.message)
    }

    @Test
    fun addContactToPersonPersonWithMoreThan10ContactsTest() {
        val person = Person("1", "gladys", 10, "Armenia")
        entityManager.persist(person)

        for (i in 1..10) {
            entityManager.persist(
                Contact(
                "$i",
                "name"+i,
                "phone${i}",
                "address$i", "correo@correo.com", person)
            )
        }

        val exc = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            contactService.addContactToPerson(
                Contact(
                    "1",
                    "juan", "97987", "dir","correo@correo.com", null
                ),
                "1"
            )
        }

        Assertions.assertEquals("Only 10 contacts by person", exc.message)
    }

    @Test
    fun addContactToPersonPersonRepeatedTest() {
        val person = Person("1", "gladys", 10, "Armenia")
        entityManager.persist(person)

        entityManager.persist(
            Contact(
            "1",
            "claudia",
            "12341234",
            "direccion",
            "correo@correo.com",
            person,
        )
        )

        val exc = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            contactService.addContactToPerson(
                Contact(
                    "2",
                    "claudia",
                    "12341234",
                    "dir",
                    "correo@correo.com",
                    null
                ),
                "1"
            )
        }
        Assertions.assertEquals("Repeated contact", exc.message)
    }

    @Test
    fun addContactToPersonHappyPathTest() {
        val person = Person("1", "gladys", 10, "Armenia")
        entityManager.persist(person)

        contactService.addContactToPerson(
            Contact(
                "2",
                "claudia",
                "12341234",
                "dir",
                "correo@correo.com",
                null
            ),
            "1"
        )

        val contact = entityManager.find(Contact::class.java, "2")

        Assertions.assertNotNull(contact)
        Assertions.assertEquals("claudia", contact.name)
        Assertions.assertEquals("dir", contact.address)
        Assertions.assertEquals("12341234", contact.phone)
        Assertions.assertEquals("1", contact.person?.id)
    }






}