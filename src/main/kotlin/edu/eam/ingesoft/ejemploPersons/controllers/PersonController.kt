package edu.eam.ingesoft.ejemploPersons.controllers

import edu.eam.ingesoft.ejemploPersons.models.Person
import edu.eam.ingesoft.ejemploPersons.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController {

    @Autowired
    lateinit var personService: PersonService

    /**
     * URI: persons: la reglas para definir la uri:
     *     1. los recursos se deben nombrar sustantivos en plural
     * Method: POST
     *
     * Params: Body
     *
     * Result: void
     *
     */
    @PostMapping("/persons") //POST http://localhost:8081/persons
    fun createPerson(@RequestBody person: Person) {
        personService.createPerson(person)
    }

    /**
     * /persons/{id} nos dice que llega un path parametro que se llama id
     * @PathVariable nos inidica que el parametro llega como patparam
     */
    @GetMapping("/persons/{id}")  //uri = /persons/10 , buscando la persona 10
    fun findPersonById(@PathVariable("id") id: String) = personService.findPerson(id)

    /*@GetMapping("/persons")  //uri = /persons/10 , buscando la persona 10
    fun findPersonById2(@RequestParam("id") id: String?): Any? {
        return if (id == null) personService.getAllPerson() else personService.findPerson(id)
    }*/

    @GetMapping("/persons")
    fun getAllPersons() = personService.getAllPerson()

    @PutMapping("/persons/{id}") //el uri apunta a una persona especifica
    fun editPerson(@PathVariable id: String, @RequestBody person: Person) {
        person.id = id
        personService.editPerson(person)
    }

    @DeleteMapping("/persons/{id}")
    fun deletePerson(@PathVariable id: String) = personService.deletePerson(id)
}