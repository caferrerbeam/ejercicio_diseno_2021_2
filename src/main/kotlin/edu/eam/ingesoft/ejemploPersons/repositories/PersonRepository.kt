package edu.eam.ingesoft.ejemploPersons.repositories

import edu.eam.ingesoft.ejemploPersons.models.Person
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Component
@Transactional
class PersonRepository {

    @PersistenceContext
    lateinit var em: EntityManager

    fun create(person: Person) {
        em.persist(person)
    }
}