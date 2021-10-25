package edu.eam.ingesoft.ejemploPersons.services

import edu.eam.ingesoft.ejemploPersons.exceptions.BusinessException
import edu.eam.ingesoft.ejemploPersons.models.entities.Person
import edu.eam.ingesoft.ejemploPersons.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PersonService {

    @Autowired
    lateinit var personRepository: PersonRepository

    /**
     * 1. no pueden haber personas repetidas(por cedula) ni personas con el mismo nombre
     */
    fun createPerson(person: Person) {
       val personById = personRepository.find(person.id?:"")

       if (personById != null) {
           //aqui lanzamos una excepcion de negocio
           throw BusinessException("This person already exists")
       }

       val personByName = personRepository.findByName(person.name)

        if (personByName != null) {
            //aqui lanzamos una excepcion de negocio
            throw BusinessException("This person with this name already exists")
        }

        personRepository.create(person)
    }

    /*
    fun editPerson(person: Person) {
        val personById = personRepository.find(person.id)

        if (personById == null) {
            throw BusinessException("This person does not exist")
        }

        personRepository.update(person)
    }
    */
    fun editPerson(person: Person) {
        // ?: elvis operator nos indica que queremos hacer si esto retorna null
        /*
        val res = personRepository.find(person.id) ?: Person()
        //en caso de que personRepository.find(person.id) retorne null, se asigna a res Person()

        val res = a ?: 1 //si a es null, res = 1
         */
        personRepository.find(person.id?:"") ?: throw EntityNotFoundException("This person does not exist")
        personRepository.update(person)
    }

    fun deletePerson(id: String) {
        personRepository.find(id) ?: throw EntityNotFoundException("This person does not exist")
        personRepository.delete(id)
    }

    fun findPerson(id: String) = personRepository.find(id) // ?: throw EntityNotFoundException("Not found")

    fun getAllPerson() = personRepository.findAll()
}