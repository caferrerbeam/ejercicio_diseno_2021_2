package edu.eam.ingesoft.ejemploPersons.repositories

import edu.eam.ingesoft.ejemploPersons.models.Contact
import edu.eam.ingesoft.ejemploPersons.models.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ContactRepositoryTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var contactRepository: ContactRepository

    @Test
    fun testFindByPerson(){
        //prerequisitos
        val person = Person("1", "claudia",31, "armenia")
        val person2 = Person("2", "jose",31, "armenia")
        entityManager.persist(person)
        entityManager.persist(person2)

        entityManager.persist(Contact("1","camilo", "3008765678", "por ahi lejos", person))
        entityManager.persist(Contact("2","juan", "436456456", "jummmm", person))
        entityManager.persist(Contact("3","fabian", "253452345", "ni idea donde vive", person))
        entityManager.persist(Contact("4","patricia", "23453245", "no se", person2))

        //ejecutar la prueba
        val contacts = contactRepository.findByPerson("1")

        //aserciones
        Assertions.assertEquals(3, contacts.size)
        //contacts.forEachIndexed { i, it -> Assertions.assertEquals("1",it.person.id) }
        contacts.forEach{ Assertions.assertEquals("1",it.person?.id) }
    }

}