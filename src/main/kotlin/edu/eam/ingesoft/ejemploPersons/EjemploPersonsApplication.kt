package edu.eam.ingesoft.ejemploPersons

import edu.eam.ingesoft.ejemploPersons.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EjemploPersonsApplication

fun main(args: Array<String>) {
	runApplication<EjemploPersonsApplication>(*args)
}

