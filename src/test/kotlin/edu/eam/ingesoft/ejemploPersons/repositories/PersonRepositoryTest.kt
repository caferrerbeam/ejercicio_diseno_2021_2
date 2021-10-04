package edu.eam.ingesoft.ejemploPersons.repositories

import edu.eam.ingesoft.ejemploPersons.models.Person
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Autowired
    lateinit var entityManager: EntityManager

    //requisitos para una buena prueba unitaria
    //1. la preuba debe ser repetible
    //2. las pruebas deben independientes entre si
    //3. la prueba siempre debe dar el mismo resultado ante los mismo parametros (deterministica)
    @Test
    fun testCreatePerson() {
        //prerequisitos
        //que la persona no exista

        //la ejecucion de la prueba.. llamar el metodo que estoy probando
        personRepository.create(Person("3", "claudia",31, "armenia"))

        //asersiones, o las verificaciones
        val person = entityManager.find(Person::class.java,  "3")
        Assertions.assertNotNull(person)
        Assertions.assertEquals("claudia", person.name)
        Assertions.assertEquals(31, person.age)
        Assertions.assertEquals("armenia", person.city)
    }

    @Test
    fun testFindAdults() {
        //prerequisitos
        entityManager.persist(Person("3", "claudia",31, "armenia"))
        entityManager.persist(Person("4", "camilo",10, "armenia"))
        entityManager.persist(Person("5", "juan",18, "armenia"))

        //ejecucion de la preubas
        val list = personRepository.findAdults()

        //assercion
        Assertions.assertEquals(2, list.size)
    }

    @Test
    fun testDelete(){
        //prerequisitos
        entityManager.persist(Person("3", "claudia",31, "armenia"))

        //ejecucion de la preuba
        personRepository.delete("3")

        //assersiones
        val person = entityManager.find(Person::class.java, "3")
        Assertions.assertNull(person)
    }

    @Test
    fun findTest() {
        entityManager.persist(Person("3", "claudia",31, "armenia"))

        val person = personRepository.find("3")

        Assertions.assertNotNull(person)
        Assertions.assertEquals("claudia", person?.name)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Person("3", "claudia",31, "armenia"))
        entityManager.flush() //persistir cambios al BD inmeditamente
        // ejecutando...
        val person = entityManager.find(Person::class.java, "3")
        person.name = "gladys"
        person.age = 10

        entityManager.clear() //borrar el entitymanger para que el que haga el update sea nuestro metodo.
        personRepository.update(person)


        //assersiones
        val personToAssert = entityManager.find(Person::class.java, "3")
        Assertions.assertEquals("gladys", personToAssert.name)
        Assertions.assertEquals(10, personToAssert.age)
    }


    @Test
    fun testFindByCityAndAgeGreaterThan() {
        //prerequisitos
        entityManager.persist(Person("3", "claudia",31, "armenia"))
        entityManager.persist(Person("4", "camilo",21, "armenia"))
        entityManager.persist(Person("5", "juan",18, "armenia"))
        entityManager.persist(Person("6", "juan",18, "cali"))
        entityManager.persist(Person("7", "juan",18, "cali"))

        //ejecucion de la preubas
        val list = personRepository.findByCityAndAgeGreaterThan("armenia", 20)

        //assercion
        Assertions.assertEquals(2, list.size)

        list.forEach {
            Assertions.assertEquals("armenia",it.city)
        }

        /*
        for (Person it: list) {
        Assertions.assertEquals("armenia",it.city)
        }
         */
    }
}