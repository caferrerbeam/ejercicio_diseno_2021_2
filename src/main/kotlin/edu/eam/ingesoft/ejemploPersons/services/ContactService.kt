package edu.eam.ingesoft.ejemploPersons.services

import edu.eam.ingesoft.ejemploPersons.exceptions.BusinessException
import edu.eam.ingesoft.ejemploPersons.models.Contact
import edu.eam.ingesoft.ejemploPersons.repositories.ContactRepository
import edu.eam.ingesoft.ejemploPersons.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContactService {

    @Autowired
    lateinit var contactRepository: ContactRepository

    @Autowired
    lateinit var personRepository: PersonRepository

    /**
     * Agregar contacto a una persona
     * 1. una persona no puede tener 2 contactos con el mismo nombre y telefono
     * 2. una persona no puede tener más de 10 contactos.
     */

    fun addContactToPerson(contact: Contact, personId: String) {
       val person = personRepository.find(personId) ?: throw BusinessException("the person does not exist")

       val contacts = contactRepository.findByPerson(personId)

        if (contacts.size == 10) {
            throw BusinessException("only 10 contacts by person")
        }

        //busco dentro de la lista si hay una persona con el mismo nombre y telefono de la quiero crear
        val personByNameAndPhone = contacts.find { it.name == contact.name && it.phone == contact.phone }

        if (personByNameAndPhone != null) {
            throw BusinessException("repeated contact")
        }

        contact.person = person
        contactRepository.create(contact)
    }

}