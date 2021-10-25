package edu.eam.ingesoft.ejemploPersons.controllers

import edu.eam.ingesoft.ejemploPersons.models.entities.Contact
import edu.eam.ingesoft.ejemploPersons.models.entities.Person
import edu.eam.ingesoft.ejemploPersons.models.requests.BorrowBookRequest
import edu.eam.ingesoft.ejemploPersons.services.ContactService
import edu.eam.ingesoft.ejemploPersons.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
/**
 * todas las operaciones que se definan en este controlador empezaran por /person
 */
@RequestMapping("/persons")
class PersonController {

    @Autowired
    lateinit var personService: PersonService

    @Autowired
    lateinit var contactService: ContactService

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
    //la URI aqui es lo que defina el requestMapping + la URI que defina el postmaping
    @PostMapping //POST http://localhost:8081/persons
    fun createPerson(@RequestBody person: Person) {
        personService.createPerson(person)
    }

    /**
     * /persons/{id} nos dice que llega un path parametro que se llama id
     * @PathVariable nos inidica que el parametro llega como patparam
     */
    @GetMapping("/{id}")  //uri = /persons/10 , buscando la persona 10
    fun findPersonById(@PathVariable("id") id: String) =
        personService.findPerson(id) ?: throw EntityNotFoundException("Person not found")


        /*@GetMapping("/persons")  //uri = /persons/10 , buscando la persona 10
        fun findPersonById2(@RequestParam("id") id: String?): Any? {
            return if (id == null) personService.getAllPerson() else personService.findPerson(id)
        }*/

        @GetMapping
        fun getAllPersons() = personService.getAllPerson()

        @PutMapping("/{id}") //el uri apunta a una persona especifica
        fun editPerson(@PathVariable id: String, @RequestBody person: Person) {
            person.id = id
            personService.editPerson(person)
        }

        @DeleteMapping("/{id}")
        fun deletePerson(@PathVariable id: String) = personService.deletePerson(id)

        //contacts/{{id}} ---> GET /contacts/1--> expresando es el contacto con id X
        //los contactos de una persona  GET /persons/{{id}}/contacts
        //todos los contactos  GET /persons/contacts (estaria esperando la info de las personas) , GET /contacts (solo la info de los contacts
        //agregar un contacto a una persona: POST  /persons/{{id}}/contacts

        @GetMapping("/{id}/contacts")
        fun getContactsByPerson(@PathVariable("id") idPersona: String) = contactService.findByPerson(idPersona)

        @PostMapping("/{id}/contacts")
        fun createContact(@PathVariable("id")idPersona: String,
                          @Validated @RequestBody contact: Contact
        ) = contactService.addContactToPerson(contact, idPersona)

        fun borrowBook(@Validated @RequestBody borrow: BorrowBookRequest) {

        }
}