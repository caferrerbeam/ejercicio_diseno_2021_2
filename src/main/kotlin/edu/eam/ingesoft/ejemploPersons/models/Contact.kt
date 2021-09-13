package edu.eam.ingesoft.ejemploPersons.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "tbl_contactos")
@Entity
data class Contact(

    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "nombre")
    var name: String,

    @Column(name = "telefono")
    var phone: String,

    @Column(name = "direccion")
    var address: String,

    //modelando una llave foranea
    @ManyToOne
    @JoinColumn(name = "cedula_persona")
    val persona: Person
)
