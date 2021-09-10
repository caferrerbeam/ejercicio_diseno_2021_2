package edu.eam.ingesoft.ejemploPersons.models

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "tbl_personas")
data class Person(
    @Id
    @Column(name = "cedula")
    val id: String,

    @Column(name = "nombre")
    var name:String,
)
