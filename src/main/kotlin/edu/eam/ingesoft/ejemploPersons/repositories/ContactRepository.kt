package edu.eam.ingesoft.ejemploPersons.repositories

import edu.eam.ingesoft.ejemploPersons.models.Contact
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ContactRepository {

    @Autowired
    lateinit var entityManager: EntityManager

    fun findByPerson(id: String): List<Contact> {
        val query = entityManager.createQuery("SELECT con FROM Contact con WHERE con.person.id = :idPerson")
        query.setParameter("idPerson", id)

        return query.resultList as List<Contact>
    }
}