package edu.eam.ingesoft.ejemploPersons.models

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_personas")
data class Person(
    @Id
    @Column(name = "cedula")
    var id: String? = "",

    @Column(name = "nombre")
    var name:String,

    @Column(name = "edad")
    var age: Int? = 0,

    @Column(name = "ciudad")
    var city: String? = ""
): Serializable
