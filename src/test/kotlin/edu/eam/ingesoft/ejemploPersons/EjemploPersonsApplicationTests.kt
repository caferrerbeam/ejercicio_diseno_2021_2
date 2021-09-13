package edu.eam.ingesoft.ejemploPersons

import edu.eam.ingesoft.ejemploPersons.models.Person
import edu.eam.ingesoft.ejemploPersons.repositories.PersonRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class EjemploPersonsApplicationTests {

	@Autowired
	lateinit var personRepository: PersonRepository;

	@Test
	fun contextLoads() {
	}

	@Test
	fun test(){
		personRepository.create(Person("1234", "camilo"))
	}

}
