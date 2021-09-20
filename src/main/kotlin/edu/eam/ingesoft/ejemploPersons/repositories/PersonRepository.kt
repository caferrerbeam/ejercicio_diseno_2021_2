package edu.eam.ingesoft.ejemploPersons.repositories

import edu.eam.ingesoft.ejemploPersons.models.Person
import org.hibernate.cache.spi.QueryCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.Query

@Component //anotacion que nos dice que esta es una clase manejada por springboot
@Transactional //para que las operaciones sobre la BD funcionen.
class PersonRepository {

    //inyeccion de depencia...... el framework se encarga de asignarle valor a la depencia
    @Autowired //esta anotacion indica que springboot se encargara de instanciar esta clase.
    lateinit var em: EntityManager //clase que nos da JPA para manipular las entidades.

    fun create(person: Person){
        em.persist(person) //inserta en la tabla que define la entidad.
    }

    //? quiere decir q algo puede ser null
    fun find(id:String): Person?{
        //se el envia la clase que quiero buscar y el valor de la llave primaria que quiero buscar.
       return em.find(Person::class.java, id) //busca en la bd por llave primaria
    }

    fun update(person: Person) {
        em.merge(person) //actualizar un registro sobre la BD
    }

    fun delete(id: String) {
        //buscan por id la entidad que quiero borrar
        val persona = find(id)

        //solo puedo borrar una persona que exista...
        if (persona!=null) {
            //borra la entidad de la BD, recibe por parametro la entidad a borrrar
            em.remove(persona)
        }
    }

    fun findAll(): List<Person> {
        /*
        Estructura de una consulta:
        SELECT <proyeccion> FROM <entidad> <objeto> WHERE <predicados>
        proyeccion: que quiero retornar en la consulta
        entidad: la entidad que va participar en la consulta
        objeto: el objeto sobre el cual se van hacer la proyecciones y los predicados, le pueden poner el nombre que quieran
        predicados: las condiciones de la consulta
         */
        val query: Query = em.createQuery("SELECT per FROM Person per")

        //ejecutar la consulta...
        return query.resultList as List<Person>
    }

    /**
     * retornar las personas mayores de edad
     */
    fun findAdults(): List<Person> {
        /**
         * en los predicados se pueden operadores <, >, =,!=, >=, <=
         * AND, OR
         */
        val query = em.createQuery("SELECT obj FROM Person obj WHERE obj.age>=18")

        return query.resultList as List<Person>
    }

    /**
     * Retorna la lista de personas de una ciudad
     */
    fun findByCity(city: String): List<Person> {
        //los parametros se definen en los predicados con : (dos puntos) y el nombre del parametro
        //por ejemplo "SELECT per FROM Person per WHERE per.city = :city AND per.age > :age" aqui hay parametros
        //city y age
        val query = em.createQuery("SELECT per FROM Person per WHERE per.city = :city")
        //definir el valor de los parametros...
        query.setParameter("city", city)

        //definirle el valor al parametro
        return query.resultList as List<Person>
    }


}