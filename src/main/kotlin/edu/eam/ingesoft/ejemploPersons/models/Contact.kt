package edu.eam.ingesoft.ejemploPersons.models

import org.hibernate.validator.constraints.*
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/*
para que una clase sea entidad debe cumplir 4 requisitos
1. que tenga un constructor vacio (no aplica para las data class en kotlin)
2. implemente serializable
3. que tenga una columna con @Id
4. que tenga la anotacion @entity
 */

//le dice a spring-boot que esta es una clase que representa una entidad(tabla) en el medio de persistencias
@Entity
@Table(name = "tbl_contactos") //esto es obligatorio si la clase y la tabla se llaman diferente
data class Contact(

    @Id
    @Column(name = "id") //es obligatorio si la columna y el atributo se llaman diferente
    val id: String,

    @field:Length(min=3, max=30)
    @field:Pattern(regexp = "[a-zA-ZàèìòùÀÈÌÒÙ]*")
    @field:NotBlank
    @Column(name = "nombre")
    var name: String,

    @Column(name = "telefono")
    @field:Pattern(regexp = "[0-9]*")
    //@field:Digits()//10.345
    @field:Length(min=10, max = 15)
    var phone: String,

    @Column(name = "direccion")
    var address: String,

    @field:Email
    @Column(name = "correo_electronico")
    var email: String? = "",

    //modelando una llave foranea
    @ManyToOne //se le puede asignar a una persona cualquier persona de la tabla relacionada
    @JoinColumn(name = "cedula_persona") // es obligatorio si la columna se llama diferente al atributo.
    var person: Person?
):Serializable //: es como decir implements en JAVA
