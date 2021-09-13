package edu.eam.ingesoft.ejemploPersons

import edu.eam.ingesoft.ejemploPersons.models.Person
import edu.eam.ingesoft.ejemploPersons.repositories.PersonRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EjemploPersonsApplicationTests {

	@Autowired
	lateinit var personRepository:PersonRepository

	@Test
	fun contextLoads() {
	}

	@Test
	fun testCreate() {
		personRepository.create(Person("2", "juan"))
	}

	@Test
	fun testFind() {
		val person = personRepository.find("3")
		println(person)
	}

	@Test
	fun testUpdate() {
		val person = personRepository.find("1")

		/*if (person!=null){
			person.name = "claudia"
		}*/
		person?.name = "claudia" // lo que hace el ?
		if (person!=null) {
			personRepository.update(person)
		} else {
			print("no existe la persona....")
		}
	}

	@Test
	fun testDelete() {
		personRepository.delete("1")
	}

}
